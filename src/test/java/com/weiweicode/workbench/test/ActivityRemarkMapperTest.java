package com.weiweicode.workbench.test;

import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.utils.UUIDUtil;
import com.weiweicode.crm.workbench.mapper.ActivityRemarkMapper;
import com.weiweicode.crm.workbench.pojo.ActivityRemark;
import org.junit.Test;

/**
 * @Author weiwei
 * @Date 2023/1/11 15:59
 * @Version 1.0
 */
public class ActivityRemarkMapperTest {

    @Test
    public void testSaveRemark(){
        ActivityRemarkMapper mapper = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkMapper.class);
        String noteContent = "23232";
        String activityId = "fasdfasdf";
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = "";
        String editFlag = "0";

        ActivityRemark ar = new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);
        int i = mapper.saveRemark(ar);
        System.out.println(i);

    }

    @Test
    public void testGetCountByAids(){
        ActivityRemarkMapper mapper = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkMapper.class);
        String[] ids = {"e4a4d1ef04b44d22b047717cc0687f70","ebd53bfc012844068e7364fcca2059b8"};
        // String[] ids = {"e4a4d1ef04b44d22b047717cc0687f70"};
        int countByAids = mapper.getCountByAids(ids);
        System.out.println(countByAids);
    }
}
