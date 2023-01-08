package com.weiweicode.workbench.test;

import com.weiweicode.crm.settings.mapper.UserMapper;
import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.settings.service.impl.UserServiceImpl;
import com.weiweicode.crm.utils.ServiceFactory;
import com.weiweicode.crm.utils.SqlSessionUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/6 0:41
 * @Version 1.0
 */
public class UserMapperTest {

    @Test
    public void testGetUserList(){
        // UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        // List<User> userList = userService.getUserList();

        UserMapper mapper = SqlSessionUtil.getSqlSession().getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();
        userList.forEach(user -> System.out.println(user.getName()));
    }

    @Test
    public void testSelectUser(){

    }
}
