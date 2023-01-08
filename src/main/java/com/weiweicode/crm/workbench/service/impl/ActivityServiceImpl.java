package com.weiweicode.crm.workbench.service.impl;

import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.vo.PaginationVO;
import com.weiweicode.crm.workbench.mapper.ActivityMapper;
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
}
