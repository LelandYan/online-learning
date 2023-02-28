package com.lelandyan.educms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lelandyan.educms.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-24
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getAllBanner();
    void pageQuery(Page<CrmBanner> bannerPage, BannerQuery bannerQuery);
}
