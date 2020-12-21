package com.offcn.project.controller;

import com.alibaba.fastjson.JSON;
import com.offcn.dycommon.response.AppResponse;
import com.offcn.dycommon.enums.ProjectStatusEnume;
import com.offcn.project.constant.ProjectConstant;
import com.offcn.project.po.TReturn;
import com.offcn.project.service.ProjectCreateService;
import com.offcn.project.vo.req.ProjectBaseInfoVo;
import com.offcn.project.vo.req.ProjectRedisStorageVo;
import com.offcn.project.vo.req.ProjectReturnVo;
import com.offcn.vo.BaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")
@Slf4j
@Api(tags = "项目基本功能模块（创建、保存、项目信息获取、文件上传等）")
public class ProjectCreateController {

    @Autowired
    private StringRedisTemplate redisTemplate ;

    @Autowired
    private ProjectCreateService projectCreateService;

    @ApiOperation("项目发起第1步-阅读同意协议")
    @GetMapping("/init")
    public AppResponse<String> init(BaseVo baseVo){

        String accessToken = baseVo.getAccessToken();
        String memberid = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberid)){
            return AppResponse.fail("无权访问");
        }
        int i = Integer.parseInt(memberid);
        String s = projectCreateService.initCreateProject(i);
        return AppResponse.ok(s);

    }

    @ApiOperation("/项目发起第2步-保存项目的基本信息")
    @PostMapping("/savebaseInfo")
    public AppResponse<String> savebaseInfo(ProjectBaseInfoVo vo){

        //获取redis中的token
        String orginal = redisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + vo.getProjectToken());
        //转化redis存储对应的vo
        ProjectRedisStorageVo storageVo = JSON.parseObject(orginal, ProjectRedisStorageVo.class);
        //页面的数据存储到redis的vo中
        BeanUtils.copyProperties(vo, storageVo);
        String jsonString = JSON.toJSONString(storageVo);
        redisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+vo.getProjectToken(), jsonString);
        return AppResponse.ok("成功");

    }


    @ApiOperation("第三部,存储项目的return值")
    @PostMapping("/savereturn")
    public AppResponse<Object> savereturnInfo(@RequestBody List<ProjectReturnVo> projectReturnVos){
        //获取redis中存储
        ProjectReturnVo projectReturnVo = projectReturnVos.get(0);
        String projectToken = projectReturnVo.getProjectToken();
        String projectContant = redisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken);

        ProjectRedisStorageVo storageVo = JSON.parseObject(projectContant, ProjectRedisStorageVo.class);

        List<TReturn> returnList = new ArrayList<>();
        for (ProjectReturnVo projectReturnVo1 : projectReturnVos){

            TReturn treturn = new TReturn() ;
            BeanUtils.copyProperties(projectReturnVo1, treturn);
            returnList.add(treturn);

        }
        //更新redis
        storageVo.setProjectReturns(returnList);
        String jsonString = JSON.toJSONString(storageVo);
        redisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken, jsonString);
        return AppResponse.ok("return success");
    }


    @ApiOperation("项目发起第4步-项目保存项目回报信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken",value = "用户令牌",required = true),
            @ApiImplicitParam(name = "projectToken",value="项目标识",required = true),
            @ApiImplicitParam(name="ops",value="用户操作类型 0-保存草稿 1-提交审核",required = true)})
    @PostMapping("/submit")

    public AppResponse<Object> submit(String accessToken,String projectToken,String ops){
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)){
            return AppResponse.fail("无权限，请先登录");
        }
        String projectJsonString = redisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken);
        ProjectRedisStorageVo storageVo = JSON.parseObject(projectJsonString, ProjectRedisStorageVo.class);
        if (!StringUtils.isEmpty(ops)){

            if(ops.equals("1")){
                //获取项目状态提交枚举
                ProjectStatusEnume submitAuth = ProjectStatusEnume.SUBMIT_AUTH;
                //保存项目信息
                projectCreateService.saveProjectInfo(submitAuth,storageVo);
                return AppResponse.ok(null);

        }else if(ops.equals("0")) {
                //获取项目 草稿状态
                ProjectStatusEnume projectStatusEnume = ProjectStatusEnume.DRAFT;
                //保存草稿
                projectCreateService.saveProjectInfo(projectStatusEnume,storageVo);
                return AppResponse.ok(null);
            }else {
                AppResponse<Object> appResponse = AppResponse.fail(null);
                appResponse.setMsg("不支持此操作");
                return appResponse;
            }
        }
        return AppResponse.fail(null);
    }

}
