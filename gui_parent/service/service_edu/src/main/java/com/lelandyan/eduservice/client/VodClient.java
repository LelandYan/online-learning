package com.lelandyan.eduservice.client;

import com.lelandyan.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(value = "service-vod", fallback = VodClientImpl.class)//指定调用的服务名，前提要注册到nacos注册中心中
public interface VodClient {
    //定义调用方法的路径
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAliVideo/{id}")
    public R removeAliVideo(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoList);
}
