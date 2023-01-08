package com.weiweicode.crm.settings.mapper;

import com.weiweicode.crm.settings.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/4 19:10
 * @Version 1.0
 */
public interface UserMapper {

    User selectUser(Map<String, String> map);

    List<User> getUserList();
}
