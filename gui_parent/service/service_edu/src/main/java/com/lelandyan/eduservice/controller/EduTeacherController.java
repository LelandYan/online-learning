package com.lelandyan.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.commonutils.R;
import com.lelandyan.eduservice.entity.EduTeacher;
import com.lelandyan.eduservice.entity.vo.TeacherQuery;
import com.lelandyan.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-18
 */
@Api(tags = "讲师管理")
@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 把service注入
    @Autowired
    private EduTeacherService teacherService;

    //rest风格
    //查询讲师表所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    //逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/deleteTeacherById/{id}")
    public R deleteTeacherById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页查询
    //page：当前页
    //limit：每页显示记录数
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageList/{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit
    ) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        //分页查询，查完后，会将数据封装在pageParam中
        teacherService.page(pageParam, null);
        //获取查询到的数据
        List<EduTeacher> records = pageParam.getRecords();
        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    //多条件查询讲师带分页
    @ApiOperation(value = "多条件查询讲师带分页")
    @PostMapping("/pageTeacherCondition/{page}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
                                  @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {//通过封装TeacherQuery对象来直接传递查询条件
        //创建分页page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);

        //调用方法实现多条件分页查询
        teacherService.pageQuery(pageParam, teacherQuery);

        //获取查询到的数据
        List<EduTeacher> records = pageParam.getRecords();

        //获取总记录数
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/save")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据id查询,用于信息回显
    @ApiOperation(value = "根据id查询")
    @GetMapping("/getTeacherById/{id}")
    public R getById(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    //修改讲师
    @ApiOperation(value = "修改讲师")
    @PostMapping("/updateTeacherById")
    public R updateById(@RequestBody EduTeacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

