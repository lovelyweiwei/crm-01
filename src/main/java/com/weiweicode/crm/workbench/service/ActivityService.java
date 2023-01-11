package com.weiweicode.crm.workbench.service;

import com.weiweicode.crm.vo.PaginationVO;
import com.weiweicode.crm.workbench.pojo.Activity;

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
}
