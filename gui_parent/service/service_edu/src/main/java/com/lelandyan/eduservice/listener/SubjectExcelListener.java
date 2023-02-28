package com.lelandyan.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lelandyan.eduservice.entity.EduSubject;
import com.lelandyan.eduservice.entity.excel.SubjectData;
import com.lelandyan.eduservice.service.EduSubjectService;
import com.lelandyan.servicebase.exceptionhandler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService subjectService;

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public SubjectExcelListener() {
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //表示excel中没有数据，就不需要读取了
        if (subjectData == null) {
            throw new GuliException(20001, "添加失败");
        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断是否有一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { //没有相同的一级分类，进行添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0"); //设置一级分类id值，0代表为一级分类
            existOneSubject.setTitle(subjectData.getOneSubjectName());//设置一级分类名
            subjectService.save(existOneSubject);//给数据库添加一级分类
        }

        //获取一级分类的id值
        String parent_id = existOneSubject.getId();
        //判断是否有耳机分类是否重复
        EduSubject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), parent_id);
        if (existTwoSubject == null) {//没有相同的二级分类，进行添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(parent_id); //设置二级分类id值
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());//设置二级分类名
            subjectService.save(existTwoSubject);//给数据库添加二级分类
        }

    }


    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name)
                .eq("parent_id", "0");
        EduSubject oneSubject = subjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name)
                .eq("parent_id", parentId);
        EduSubject twoSubject = subjectService.getOne(wrapper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
