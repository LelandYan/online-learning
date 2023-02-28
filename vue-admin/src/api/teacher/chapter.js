import request from '@/utils/request' //引入已经封装好的axios 和 拦截器

export default {
    //根据课程id获取章节和小节数据列表
    getChapterVideoByCourseId(courseId) {
        return request({
            url: `/eduservice/chapter/getChapterVideo/${courseId}`,
            method: 'get',
        })
    },
    //添加章节
    addChapter(chapter) {
        return request({
            url: `/eduservice/chapter/addChapter`,
            method: `post`,
            data: chapter
        })
    },
    //根据id查询章节
    getChapterById(chapterID) {
        return request({
            url: `/eduservice/chapter/getChapter/${chapterID}`,
            method: `get`,
        })
    },
    //修改章节
    updateChapter(chapter) {
        return request({
            url: `/eduservice/chapter/updateChapter`,
            method: `post`,
            data: chapter
        })
    },
    //删除章节
    deleteById(chapterID) {
        return request({
            url: `/eduservice/chapter/deleteById/${chapterID}`,
            method: `delete`,
        })
    }
}
