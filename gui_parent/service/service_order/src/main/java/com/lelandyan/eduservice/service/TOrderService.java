package com.lelandyan.eduservice.service;

import com.lelandyan.eduservice.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-28
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String memberId);
}
