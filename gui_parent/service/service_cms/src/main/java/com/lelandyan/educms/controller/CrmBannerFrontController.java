package com.lelandyan.educms.controller;


import com.lelandyan.commonutils.R;
import com.lelandyan.educms.entity.CrmBanner;
import com.lelandyan.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-24
 */
@RestController
@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有幻灯片
    @GetMapping("/getAllBanner")
    public R getAll() {
        List<CrmBanner> list = crmBannerService.getAllBanner();
        return R.ok().data("list", list);
    }
}

