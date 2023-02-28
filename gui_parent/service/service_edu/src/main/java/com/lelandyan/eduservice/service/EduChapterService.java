package com.lelandyan.eduservice.service;

import com.lelandyan.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lelandyan.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
