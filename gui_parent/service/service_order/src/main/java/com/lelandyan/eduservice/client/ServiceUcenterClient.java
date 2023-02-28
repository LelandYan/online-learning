package com.lelandyan.eduservice.client;

import com.lelandyan.eduservice.entity.vo.EduCourseVo;
import com.lelandyan.eduservice.entity.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter")
public interface ServiceUcenterClient {

    //根据用户id，获取用户信息
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable("memberId") String memberId);

}

