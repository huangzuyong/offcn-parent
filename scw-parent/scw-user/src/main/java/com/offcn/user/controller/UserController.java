package com.offcn.user.controller;

import com.offcn.dycommon.response.AppResponse;
import com.offcn.user.bean.User;
import com.offcn.user.po.TMember;
import com.offcn.user.service.UserService;
import com.offcn.user.vo.resp.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试类")
@RestController
public class UserController {

    @ApiOperation("hello日志")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "姓名",required = true)
    })
    @GetMapping("/hello")
    public String hello(String name){
        return "OK:"+name;
    }

    @ApiOperation("保存用户")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true),
            @ApiImplicitParam(name="email",value="邮箱")
    })
    @PostMapping("/user")
    public User save(String name, String email){
        User user  = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }




}
