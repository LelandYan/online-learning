package com.lelandyan.eduservice.client;

import com.lelandyan.commonutils.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    UcenterMember getMemberInfoById(@PathVariable("memberId") String memberId);
}

