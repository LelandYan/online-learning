package com.lelandyan.eduservice.service;

import com.lelandyan.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lelandyan.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-21
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
