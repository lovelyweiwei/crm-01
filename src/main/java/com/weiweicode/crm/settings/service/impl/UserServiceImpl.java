package com.weiweicode.crm.settings.service.impl;

import com.weiweicode.crm.exceptions.LoginException;
import com.weiweicode.crm.settings.mapper.UserMapper;
import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/4 19:22
 * @Version 1.0
 */
public class UserServiceImpl implements UserService {

    private UserMapper userMapper = SqlSessionUtil.getSqlSession().getMapper(UserMapper.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userMapper.selectUser(map);
        // System.out.println(user);

        if(user == null){
            throw new LoginException("账号密码错误");
        }

        //如果程序能够成功的执行到该行，说明账号密码正确
        //需要继续向下验证其他3项信息

        //验证失效时间
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0){
            throw new LoginException("账号已失效");
        }

        //判断锁定状态
        if ("0".equals(user.getLockState())) {
            throw new LoginException("账号已锁定");
        }

        //判断ip地址
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("ip地址受限");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }
}
