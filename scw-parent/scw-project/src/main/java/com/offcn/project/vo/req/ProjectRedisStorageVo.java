package com.offcn.project.vo.req;

import com.offcn.project.po.TReturn;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRedisStorageVo {

    private String projectToken ; //临时的token
    private Integer memberId ; //会员的id

    private List<Integer> typeids ;
    private List<Integer> tagids ;

    private String name;//项目名称
    private String remark;//项目简介
    private Integer money;//筹资金额
    private Integer day;//筹资天数
    private String headerImage;//项目头部图片
    private List<String> detailsImage;//项目详情图片
    private List<TReturn> projectReturns;//项目回报

}
