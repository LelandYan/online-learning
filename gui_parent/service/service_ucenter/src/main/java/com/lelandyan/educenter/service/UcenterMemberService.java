package com.lelandyan.educenter.service;

import com.lelandyan.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lelandyan.educenter.entity.vo.LoginVo;
import com.lelandyan.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-25
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getMemberByOpenId(String openid);

    Integer getCountRegister(String day);
}
