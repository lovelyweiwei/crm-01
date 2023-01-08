package com.weiweicode.crm.settings.service;

import com.weiweicode.crm.exceptions.LoginException;
import com.weiweicode.crm.settings.pojo.User;

import java.util.List;

/**
 * @Author weiwei
 * @Date 2023/1/4 19:22
 * @Version 1.0
 */
public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
