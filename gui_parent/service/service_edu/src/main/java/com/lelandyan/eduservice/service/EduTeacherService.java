package com.lelandyan.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lelandyan.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-18
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);

    Map<String, Object> getTeacherFrontPageList(Page<EduTeacher> teacherPage);
}
