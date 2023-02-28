package com.lelandyan.eduservice.service.impl;

import com.lelandyan.eduservice.client.ServiceEduClient;
import com.lelandyan.eduservice.client.ServiceUcenterClient;
import com.lelandyan.eduservice.entity.TOrder;
import com.lelandyan.eduservice.entity.vo.EduCourseVo;
import com.lelandyan.eduservice.entity.vo.UcenterMemberVo;
import com.lelandyan.eduservice.mapper.TOrderMapper;
import com.lelandyan.eduservice.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lelandyan.eduservice.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-28
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    //远程调用serviceEdu
    @Autowired
    private ServiceEduClient serviceEduClient;

    //远程调用serviceUcenter
    @Autowired
    private ServiceUcenterClient serviceUcenterClient;

    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
        //根据用户id，获取用户信息
        UcenterMemberVo memberInfo = serviceUcenterClient.getMemberInfoById(memberId);

        //根据课程id，获取课程信息
        EduCourseVo courseInfo = serviceEduClient.getCourseInfoByIdOrder(courseId);

        TOrder tOrder = new TOrder();
        tOrder.setMobile(memberInfo.getMobile());
        tOrder.setNickname(memberInfo.getNickname());
        tOrder.setMemberId(memberId);
        tOrder.setCourseCover(courseInfo.getCover());
        tOrder.setCourseId(courseId);
        tOrder.setCourseTitle(courseInfo.getTitle());
        tOrder.setTeacherName(courseInfo.getTeacherName());
        tOrder.setTotalFee(courseInfo.getPrice());
        tOrder.setStatus(0);//支付状态：（ 0：已支付，1：未支付 ）
        tOrder.setPayType(1);//支付类型： 1：微信 ， 2：支付宝
        tOrder.setOrderNo(OrderNoUtil.getOrderNo()); //订单号

        //保存订单
        baseMapper.insert(tOrder);

        //返回订单号
        return tOrder.getOrderNo();
    }

}
