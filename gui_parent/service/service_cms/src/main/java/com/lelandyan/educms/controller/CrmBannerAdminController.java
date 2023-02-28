package com.lelandyan.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lelandyan.commonutils.R;
import com.lelandyan.educms.entity.CrmBanner;
import com.lelandyan.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/educms/banneradmin")
public class CrmBannerAdminController {
    //后台banner管理接口
    @Autowired
    private CrmBannerService crmBannerService;

    //条件分页查询banner

    //分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageBanner = new Page<>(page, limit);
        crmBannerService.page(pageBanner, null);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    //添加banner

    @PostMapping("/addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = crmBannerService.save(crmBanner);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //修改banner
    @PostMapping("/updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner) {
        boolean flag = crmBannerService.updateById(crmBanner);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据id删除banner
    @DeleteMapping("/deleteBannerById/{id}")
    public R deleteBannerById(@PathVariable String id) {
        boolean flag = crmBannerService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据id查询banner
    @ApiOperation(value = "获取Banner")
    @GetMapping("/getBannerById/{id}")
    public R getBannerById(@PathVariable String id) {
        CrmBanner crmBanner = crmBannerService.getById(id);
        return R.ok().data("item", crmBanner);
    }

}

