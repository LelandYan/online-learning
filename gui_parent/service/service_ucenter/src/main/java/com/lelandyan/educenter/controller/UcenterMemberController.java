package com.lelandyan.educenter.controller;


import com.lelandyan.commonutils.JwtUtils;
import com.lelandyan.commonutils.R;
import com.lelandyan.educenter.entity.UcenterMember;
import com.lelandyan.educenter.entity.vo.LoginVo;
import com.lelandyan.educenter.entity.vo.RegisterVo;
import com.lelandyan.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-25
 */
@RestController
@CrossOrigin
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        //返回token，使用jwt生成
        String token = ucenterMemberService.login(loginVo);
        return R.ok().data("token", token);
    }

    //注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getUserInfoForJwt(HttpServletRequest request) {
        //调用jwt工具类里面的根据request对象，获取头信息，返回用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库，根据用户id，获取用户信息
        UcenterMember member = ucenterMemberService.getById(id);
        return R.ok().data("userInfo", member);
    }

    //根据用户id查询用户信息
    @PostMapping("/getMemberInfoById/{memberId}")
    public UcenterMember getMemberInfoById(@PathVariable("memberId") String memberId) {
        UcenterMember uncenterMember = ucenterMemberService.getById(memberId);
        UcenterMember  member = new UcenterMember();
        BeanUtils.copyProperties(uncenterMember,member);
        return member;
    }

    //根据日期，获取那天注册人数
    @GetMapping("/countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = ucenterMemberService.getCountRegister(day);
        return R.ok().data("countRegister",count);
    }

}

