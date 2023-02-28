package com.lelandyan.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lelandyan.commonutils.JwtUtils;
import com.lelandyan.commonutils.MD5;
import com.lelandyan.educenter.entity.UcenterMember;
import com.lelandyan.educenter.entity.vo.LoginVo;
import com.lelandyan.educenter.entity.vo.RegisterVo;
import com.lelandyan.educenter.mapper.UcenterMemberMapper;
import com.lelandyan.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelandyan.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-25
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //登录的方法
    @Override
    public String login(LoginVo loginVo) {
        //获取手机号和密码
        String phone = loginVo.getMobile();
        String password = loginVo.getPassword();
        //判断输入的手机号和密码是否为空
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)) {
            throw new GuliException(20001, "手机号或密码为空");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", phone);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember == null) {
            throw new GuliException(20001, "手机号不存在");
        }

        //判断密码是否正确
        // MD5加密是不可逆性的，不能解密，只能加密
        //将获取到的密码经过MD5加密与数据库比较
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001, "密码不正确");
        }

        //判断用户是否禁用
        if (ucenterMember.getIsDisabled()) {
            throw new GuliException(20001, "用户被禁用");
        }

        //生成jwtToken
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;

    }

    //注册的方法
    @Override
    public void register(RegisterVo registerVo) {
        //获取前端传来的数据
        String nickname = registerVo.getNickname(); //昵称
        String code = registerVo.getCode(); //验证码
        String mobile = registerVo.getMobile(); //手机号
        String password = registerVo.getPassword(); //密码

        //非空判断
        if (StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "传来的数据有空值，注册失败");
        }

        //判断验证码
        //获取redis验证码，根据手机号获取
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "注册失败");
        }
        //阿里云没审核通过，尴尬，就先直接定死一个验证码好了
//        if (!code.equals("7777")) {
//            throw new GuliException(20001, "验证码不正确，注册失败");
//        }

        //手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count >= 1) {
            throw new GuliException(20001, "手机号重复，注册失败");
        }

        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setPassword(MD5.encrypt(password));//密码加密
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("https://lelandyan-1010.oss-cn-beijing.aliyuncs.com/2023/02/22/act.jpg");
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getMemberByOpenId(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer getCountRegister(String day) {
        return baseMapper.getCountRegister(day);
    }
}
