import request from '@/utils/request'
export default {
    //登录的方法
  submitLoginUser(userinfo) {
    return request({
      url: `/educenter/member/login`,
      method: 'post',
      data:userinfo
    })
  },

  //根据token获取用户信息的方法
  getLoginUserInfo(){
    return request({
      url: `/educenter/member/getMemberInfo`,
      method: 'get'
    })
  }
}
