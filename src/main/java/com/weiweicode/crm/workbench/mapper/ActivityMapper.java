package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.Activity;
import jakarta.servlet.RequestDispatcher;
import org.apache.ibatis.annotations.Param;

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

    int delete(@Param("ids") String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
