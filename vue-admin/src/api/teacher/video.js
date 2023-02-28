import request from '@/utils/request' //引入已经封装好的axios 和 拦截器

export default {
    //添加小节
    addVideo(video) {
        return request({
            url: `/eduservice/video/addVideo`,
            method: `post`,
            data: video
        })
    },
    //根据id查询小节
    getVideoById(videoId) {
        return request({
            url: `/eduservice/video/getVideoById/${videoId}`,
            method: `get`,
        })
    },
    //修改小节
    updateVideo(video) {
        return request({
            url: `/eduservice/video/updateVideo`,
            method: `post`,
            data: video
        })
    },
    //删除小节
    deleteById(videoId) {
        return request({
            url: `/eduservice/video/deleteVideo/${videoId}`,
            method: `delete`,
        })
    },
    deleteAlivod(id) {
        return request({
            url: `/eduvod/video/removeAliVideo/${id}`,
            method: `delete`,
        })
    },
}
