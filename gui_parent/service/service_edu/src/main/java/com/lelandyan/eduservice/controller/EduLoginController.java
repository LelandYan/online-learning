package com.lelandyan.eduservice.controller;

import com.lelandyan.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

//@Api(tags = "讲师管理")
@RestController
@CrossOrigin
@Api(tags = "登录管理")
@RequestMapping("/eduservice/user")
public class EduLoginController {

    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://img0.baidu.com/it/u=1705694933,4002952892&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281");
    }
}
