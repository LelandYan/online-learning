import request from '@/utils/request'
export default {
    //查询前梁banner数据
  getListBanner() {
    return request({
      url: `/educms/bannerfront/getAllBanner`,
      method: 'get'
    })
  }
}