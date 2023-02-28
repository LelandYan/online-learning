package com.lelandyan.eduservice.mapper;

import com.lelandyan.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lelandyan.eduservice.entity.vo.CoursePublishVo;
import com.lelandyan.eduservice.entity.vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo getPublishCourseInfo(String courseId);

    //前台根据课程id，查询课程基础信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
