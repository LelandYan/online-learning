package com.lelandyan.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.commonutils.R;
import com.lelandyan.eduservice.entity.EduCourse;
import com.lelandyan.eduservice.entity.EduTeacher;
import com.lelandyan.eduservice.service.EduCourseService;
import com.lelandyan.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService courseService;

    //前台系统分页查询讲师的方法
    //page：当前页 ，limit：显示记录数
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontPageList(@PathVariable Long page, @PathVariable Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        Map<String, Object> map = eduTeacherService.getTeacherFrontPageList(teacherPage);

        //返回分页中的所有数据
        return R.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);
        //根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }

}

