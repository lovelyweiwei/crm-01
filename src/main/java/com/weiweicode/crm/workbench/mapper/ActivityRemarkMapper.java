package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.ActivityRemark;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author weiwei
 * @Date 2023/1/6 0:57
 * @Version 1.0
 */
public interface ActivityRemarkMapper {
    int getCountByAids(@Param("ids") String[] ids);

    int deleteByAids(@Param("ids") String[] ids);

    List<ActivityRemark> getRemarkListByAid(String id);

    int deleteByAid(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
