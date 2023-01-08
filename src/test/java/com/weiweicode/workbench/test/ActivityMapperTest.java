package com.weiweicode.workbench.test;

import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.workbench.mapper.ActivityMapper;
import com.weiweicode.crm.workbench.pojo.Activity;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/7 16:05
 * @Version 1.0
 */
public class ActivityMapperTest {

    @Test
    public void testPageList(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        ActivityMapper mapper = sqlSession.getMapper(ActivityMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name","发传单");
        map.put("startDate","2011-2-5");
        map.put("skipCount",0);
        map.put("pageSize",3);
        List<Activity> activityList = mapper.getActivityListByCondition(map);
        activityList.forEach(activity -> System.out.println(activity.getName()));
    }

    @Test
    public void testTotal(){
        SqlSession sqlSession = SqlSessionUtil.getSqlSession();
        ActivityMapper mapper = sqlSession.getMapper(ActivityMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("name","发传单2");
        map.put("skipCount",0);
        map.put("pageSize",3);
        int total = mapper.getTotalByCondition(map);
        System.out.println(total);
    }
}
