package com.weiweicode.workbench.test;

import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.workbench.mapper.ActivityRemarkMapper;
import org.junit.Test;

/**
 * @Author weiwei
 * @Date 2023/1/11 15:59
 * @Version 1.0
 */
public class ActivityRemarkMapperTest {
    @Test
    public void testGetCountByAids(){
        ActivityRemarkMapper mapper = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkMapper.class);
        String[] ids = {"e4a4d1ef04b44d22b047717cc0687f70","ebd53bfc012844068e7364fcca2059b8"};
        // String[] ids = {"e4a4d1ef04b44d22b047717cc0687f70"};
        int countByAids = mapper.getCountByAids(ids);
        System.out.println(countByAids);
    }
}
