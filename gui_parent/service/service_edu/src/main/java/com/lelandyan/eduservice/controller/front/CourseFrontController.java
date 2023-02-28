package com.lelandyan.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.commonutils.JwtUtils;
import com.lelandyan.commonutils.R;
import com.lelandyan.eduservice.client.OrderClient;
import com.lelandyan.eduservice.entity.EduCourse;
import com.lelandyan.eduservice.entity.chapter.ChapterVo;
import com.lelandyan.eduservice.entity.vo.CourseFrontVo;
import com.lelandyan.eduservice.entity.vo.CourseWebVo;
import com.lelandyan.eduservice.service.EduChapterService;
import com.lelandyan.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;
    //条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);
        return R.ok().data(map);
    }

//    //课程详情的方法
//    @GetMapping("/getFrontCourseInfo/{courseId}")
//    public R getFrontCourseInfo(@PathVariable String courseId) {
//        //根据课程id，编写sql语句查询课程信息
//        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
//
//        //根据课程id，查询章节和小节信息
//        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
//
//        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
//
//    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id查询课程基本信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节

        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        System.out.println("***********"+request);
        //根据课程id和用户id查询当前课程是否已经支付过
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(memberId);
        System.out.println(courseId);
        System.out.println(orderClient);
        boolean isBuy = orderClient.isBuyCourse(courseId, memberId);
        System.out.println(isBuy);
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",isBuy);
    }

    //根据课程id,远程调用
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVo getCourseInfoOrder(@PathVariable String id){
        CourseWebVo baseCourseInfo = courseService.getBaseCourseInfo(id);
        return baseCourseInfo;
    }

}