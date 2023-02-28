package com.lelandyan.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lelandyan.commonutils.R;
import com.lelandyan.staservice.client.UcenterRegisterClient;
import com.lelandyan.staservice.entity.StatisticsDaily;
import com.lelandyan.staservice.mapper.StatisticsDailyMapper;
import com.lelandyan.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lelandyan
 * @since 2023-02-28
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterRegisterClient ucenterRegisterClient;

    //统计某一天注册人数
    @Override
    public void createStatisticsByDay(String day) {
        //添加之前先删除表相同的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);


        //远程调用得到某一天的注册人数
        R registerR = ucenterRegisterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        //其他的数据类似，也是通过远程调用，获取数据返回即可，下面使用随机数模拟

        //把获取到的数据
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister);//注册人数
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        daily.setLoginNum(RandomUtils.nextInt(200, 300));//登录数
        daily.setVideoViewNum(RandomUtils.nextInt(200, 300));//视频流量数
        daily.setDateCalculated(day);//统计日期

        //添加到数据库中
        baseMapper.insert(daily);

    }

    //图表显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated", type);
        wrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dailyList = baseMapper.selectList(wrapper);

        //前端要求数组json结果，对应后端为List集合
        //创建两个list集合，一个放日期X轴，一个放数量Y轴
        List<String> xlist = new ArrayList<>();
        List<Integer> ylist = new ArrayList<>();

        for (StatisticsDaily daily : dailyList) {
            xlist.add(daily.getDateCalculated());

            //判断查询的哪个字段
            if ("register_num".equals(type)) {
                ylist.add(daily.getRegisterNum());
            }
            if ("login_num".equals(type)) {
                ylist.add(daily.getLoginNum());
            }
            if ("video_view_num".equals(type)) {
                ylist.add(daily.getVideoViewNum());
            }
            if ("course_num".equals(type)) {
                ylist.add(daily.getCourseNum());
            }

        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xlist", xlist);
        map.put("ylist", ylist);

        return map;
    }
}
