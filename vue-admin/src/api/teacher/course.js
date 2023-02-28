import request from '@/utils/request' //引入已经封装好的axios 和 拦截器

export default {
    //添加课程信息功能
    addCourseInfo(courseInfo) {
        return request({
            url: "/eduservice/course/addCourseInfo",
            method: 'post',
            data: courseInfo,
        })
    },
    //查询所有讲师
    getAllTeacher() {
        return request({
            url: "/eduservice/teacher/findAll",
            method: 'get',
        })
    },
    //根据课程id 查询课程基本信息
    getCourseInfoById(courseId) {
        return request({
            url: `/eduservice/course/getCourseInfoById/${courseId}`,
            method: 'get',
        })
    },
    //修改课程信息
    updateCourseInfo(courseInfoVo) {
        return request({
            url: "/eduservice/course/updateCourseInfo",
            method: 'post',
            data: courseInfoVo,
        })
    },
    //课程确认信息显示
    getPublishCourseInfo(courseId) {
        return request({
            url: "/eduservice/course/getPublishCourseInfo/" + courseId,
            method: 'get',
        })
    },
    //课程最终发布
    publishCourse(id) {
        return request({
            url: "/eduservice/course/publishCourse/" + id,
            method: 'post',
        })
    },
    getCourseListPage() {
        return request({
            url: "/eduservice/course/findAll",
            method: 'get',
        })
    },
    getCourseListPage(page, limit, courseQuery) {
        return request({
            url: `/eduservice/course/pageCourseCondition/${page}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },
    deleteCourseById(id) {
        return request({
            url: `/eduservice/course/deleteCourseById/${id}`,
            method: "delete",
        });
    },
}
