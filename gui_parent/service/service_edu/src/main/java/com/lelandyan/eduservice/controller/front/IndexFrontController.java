package com.lelandyan.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lelandyan.commonutils.R;
import com.lelandyan.eduservice.entity.EduCourse;
import com.lelandyan.eduservice.entity.EduTeacher;
import com.lelandyan.eduservice.service.EduCourseService;
import com.lelandyan.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("/index")
    public R index() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourseList = courseService.list(wrapper);

        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id");
        wrapper2.last("limit 4");
        List<EduTeacher> eduTeacherList = teacherService.list(wrapper2);
        return R.ok().data("eduList", eduCourseList).data("teacherList", eduTeacherList);
    }
}
