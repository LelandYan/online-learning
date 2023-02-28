package com.lelandyan.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.commonutils.R;
import com.lelandyan.eduservice.entity.EduCourse;
import com.lelandyan.eduservice.entity.EduTeacher;
import com.lelandyan.eduservice.entity.vo.*;
import com.lelandyan.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-22
 */
@RestController
@Api(tags = "课程管理")
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("/findAll")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list", list);
    }

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("/getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("CourseInfoVo", courseInfo);
    }

    //修改课程信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo publishCourseInfo = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse", publishCourseInfo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourse.setId(id);
        boolean flag = eduCourseService.updateById(eduCourse);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @DeleteMapping("/deleteCourseById/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }

    //分页查询
    //page：当前页
    //limit：每页显示记录数
    @ApiOperation(value = "分页课程列表")
    @GetMapping("/pageList/{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit
    ) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        //分页查询，查完后，会将数据封装在pageParam中
        eduCourseService.page(pageParam, null);
        //获取查询到的数据
        List<EduCourse> records = pageParam.getRecords();
        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //多条件查询讲师带分页
    @ApiOperation(value = "课程条件查询分页方法")
    @PostMapping("/pageCourseCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                  @RequestBody(required = false) CourseQuery courseQuery) {//通过封装TeacherQuery对象来直接传递查询条件
        //创建分页page对象
        Page<EduCourse> pageParam = new Page<>(page, limit);

        //调用方法实现多条件分页查询
        eduCourseService.pageQuery(pageParam, courseQuery);

        //获取查询到的数据
        List<EduCourse> records = pageParam.getRecords();

        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //根据课程id，查询课程信息【订单】
    @PostMapping("/getCourseInfoByIdOrder/{courseId}")
    public EduCourseVo getCourseInfoByIdOrder(@PathVariable String courseId) {
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(courseId);

        EduCourseVo eduCourseVo = new EduCourseVo();
        BeanUtils.copyProperties(courseInfo, eduCourseVo);

        return eduCourseVo;

    }

}

