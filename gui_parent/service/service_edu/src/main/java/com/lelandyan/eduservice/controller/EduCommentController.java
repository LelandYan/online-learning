package com.lelandyan.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.commonutils.JwtUtils;
import com.lelandyan.commonutils.R;
import com.lelandyan.commonutils.UcenterMember;
import com.lelandyan.eduservice.client.UcenterClient;
import com.lelandyan.eduservice.entity.EduComment;
import com.lelandyan.eduservice.service.EduCommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-27
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduCommentService commentService;

    @ApiOperation(value = "添加评论")
    @PostMapping("/auth/addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        //根据token获取用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)) {
            return R.error().message("请登录");
        }
        eduComment.setMemberId(memberId);
        UcenterMember member = ucenterClient.getMemberInfoById(memberId);

        eduComment.setNickname(member.getNickname());
        eduComment.setAvatar(member.getAvatar());
//        BeanUtils.copyProperties(member,eduComment);
        commentService.save(eduComment);
        return R.ok();
    }

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("/getCommentPage/{page}/{limit}")
    public R index(@PathVariable Long page,
                   @PathVariable Long limit,
                   String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);

        commentService.page(pageParam, wrapper);
        List<EduComment> commentList = pageParam.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }
}

