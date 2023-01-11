package com.weiweicode.crm.workbench.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Author weiwei
 * @Date 2023/1/6 0:57
 * @Version 1.0
 */
public interface ActivityRemarkMapper {
    int getCountByAids(@Param("ids") String[] ids);

    int deleteByAids(@Param("ids") String[] ids);
}
