package com.weiweicode.crm.workbench.service.impl;

import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.vo.PaginationVO;
import com.weiweicode.crm.workbench.mapper.ActivityMapper;
import com.weiweicode.crm.workbench.mapper.ActivityRemarkMapper;
import com.weiweicode.crm.workbench.pojo.Activity;
import com.weiweicode.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/5 23:19
 * @Version 1.0
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityMapper activityMapper = SqlSessionUtil.getSqlSession().getMapper(ActivityMapper.class);
    private ActivityRemarkMapper activityRemarkMapper = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkMapper.class);

    @Override
    public boolean save(Activity activity) {

        boolean flag = true;

        int count = activityMapper.save(activity);
        if (count != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得datalist
        List<Activity> dataList = activityMapper.getActivityListByCondition(map);

        //取得total
        int count = activityMapper.getTotalByCondition(map);

        //封装vo
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(count);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;

        //查询需要删除的备注数量
        int count1 = activityRemarkMapper.getCountByAids(ids);

        //删除备注信息，返回实际删除的数量
        int count2 = activityRemarkMapper.deleteByAids(ids);

        if (count1 != count2) {
            flag = false;
        }

        //删除市场活动
        int count3 = activityMapper.delete(ids);
        if (count3 != ids.length) {
            flag = false;
        }

        return flag;
    }
}
