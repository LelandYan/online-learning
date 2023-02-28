package com.lelandyan.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAliVideo(MultipartFile file);
    void removeMoreAliVideo(List<String> videoList);

    void removeMoreAlyVideo(List<String> videoList);
}
