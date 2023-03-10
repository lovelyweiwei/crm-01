package com.weiweicode.crm.workbench.service;

import com.weiweicode.crm.vo.PaginationVO;
import com.weiweicode.crm.workbench.pojo.Activity;
import com.weiweicode.crm.workbench.pojo.ActivityRemark;
import jakarta.servlet.RequestDispatcher;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/5 23:18
 * @Version 1.0
 */
public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String id);

    boolean deleteRemarkByAid(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
