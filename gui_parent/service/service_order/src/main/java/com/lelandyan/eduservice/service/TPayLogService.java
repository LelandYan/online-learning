package com.lelandyan.eduservice.service;

import com.lelandyan.eduservice.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-28
 */
public interface TPayLogService extends IService<TPayLog> {

    Map<String, Object> createWxQrcode(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
