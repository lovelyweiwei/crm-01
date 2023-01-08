package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.Activity;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/5 23:16
 * @Version 1.0
 */
public interface ActivityMapper {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);
}