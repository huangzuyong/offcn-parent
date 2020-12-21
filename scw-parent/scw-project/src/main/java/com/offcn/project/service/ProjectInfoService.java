package com.offcn.project.service;

import com.offcn.project.po.*;

import java.util.List;

public interface ProjectInfoService {

    /*获取回报的列表*/
    public List<TReturn> getProjectReturn(Integer projectId);
    /**
     * 获取系统中所有项目
     * @return
     */
    List<TProject> getAllProjects();

    /**
     * 获取项目图片
     * @param id
     * @return
     */
    List<TProjectImages> getProjectImages(Integer id);

    TProject getProjectInfo(Integer projectId);

    List<TTag> getAllProjectTags();

    List<TType> getProjectTypes();

    TReturn getReturnInfo(Integer returnId);

}
