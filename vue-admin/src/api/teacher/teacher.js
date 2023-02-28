import request from '@/utils/request'

export default {
    getTeacherListPage(page, limit, taecherQuery) {
        return request({
            // url: '/eduservice/edu-teacher/pageTeacherCondition/'+page+'/'+limit,
            url: `/eduservice/teacher/pageTeacherCondition/${page}/${limit}`,
            method: 'post',
            //teacherQuery条件对象，如果后端使用RequestBody获取数据
            //data表示把对象转换成json对象进行传递到接口里
            data: taecherQuery
        })
    },

    deleteTeacherById(id) {
        return request({
            url: `/eduservice/teacher/deleteTeacherById/${id}`,
            method: "delete",
        });
    },

    //新增讲师
    saveTeacher(teacher) {
        return request({
            url: `/eduservice/teacher/save`,
            method: `post`,
            data: teacher
        })
    },

    getTeacherInfo(id) {
        return request({
            url: `/eduservice/teacher/getTeacherById/${id}`,
            method: `get`
        })
    },
    //修改讲师信息
    updateTeacherInfo(teacher) {
        return request({
            url: `/eduservice/teacher/updateTeacherById`,
            method: `post`,
            data: teacher
        })
    }

}