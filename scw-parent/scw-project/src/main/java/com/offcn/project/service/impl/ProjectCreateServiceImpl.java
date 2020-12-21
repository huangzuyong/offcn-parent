package com.offcn.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.constant.ProjectConstant;
import com.offcn.project.enums.ProjectImageTypeEnume;
import com.offcn.project.mapper.*;
import com.offcn.project.po.*;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {

    @Autowired
    private StringRedisTemplate redisTemplate ;

    @Autowired
    private TProjectMapper projectMapper ;

    @Autowired
    private TProjectImagesMapper projectImagesMapper ;

    @Autowired
    private TProjectTypeMapper projectTypeMapper ;

    @Autowired
    private TProjectTagMapper projectTagMapper ;

    @Autowired
    private TReturnMapper returnMapper ;

    @Override
    public String initCreateProject(Integer memberid) {

        String token = UUID.randomUUID().toString().replace("-", "");

        ProjectRedisStorageVo storageVo = new ProjectRedisStorageVo();
        storageVo.setMemberId(memberid);

        String jsonString = JSON.toJSONString(storageVo);
        redisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+token, jsonString);
        return token;
    }

    @Override
    public void saveProjectInfo(ProjectStatusEnume auth, ProjectRedisStorageVo project) {

        TProject projectBase = new TProject();
        BeanUtils.copyProperties(project, projectBase);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        projectBase.setCreatedate(df.format(new Date()));
        projectMapper.insertSelective(projectBase);
        Integer id = projectBase.getId();//获取插入的id
        String headerImage = project.getHeaderImage();//获取vo中的头图
        TProjectImages  headImage = new TProjectImages(null, id, headerImage, ProjectImageTypeEnume.HEADER.getCode());
        //保存头图
        projectImagesMapper.insertSelective(headImage);
        //详细图
        List<String> detailsImage = project.getDetailsImage();
        for (String detail : detailsImage){
            TProjectImages detailImage = new TProjectImages(null, id, detail, ProjectImageTypeEnume.DETAILS.getCode());
            projectImagesMapper.insertSelective(detailImage);

            //保存标签
            List<Integer> tagids = project.getTagids();
            for (Integer tagId : tagids){
                TProjectTag tag  =  new TProjectTag(null, id, tagId);
                projectTagMapper.insertSelective(tag);
            }

            //保存分类信息
            List<Integer> typeids = project.getTypeids();
            for (Integer typeid : typeids){
                TProjectType type = new TProjectType(null, id, typeid);
                projectTypeMapper.insertSelective(type);
            }

            //保存回报信息
            List<TReturn> projectReturns = project.getProjectReturns();
            for (TReturn returnInfo : projectReturns){
                returnInfo.setProjectid(id);
                returnMapper.insertSelective(returnInfo);
            }

            redisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX+project.getProjectToken());

        }

    }
}
