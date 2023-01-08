package com.weiweicode.crm.settings.web.controller;

import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.settings.service.impl.UserServiceImpl;
import com.weiweicode.crm.utils.MD5Util;
import com.weiweicode.crm.utils.PrintJson;
import com.weiweicode.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/4 19:30
 * @Version 1.0
 */
@WebServlet("/settings/user/login.do")
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入到用户控制器");

        //获取参数
        String servletPath = request.getServletPath();

        if ("/settings/user/login.do".equals(servletPath)) {
            login(request,response);
        } else if ("/xx/xx/xx.do".equals(servletPath)) {
            //  do xxxxx......
        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response)  {

        System.out.println("进入到验证登录操作");

        //获取参数
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");


        //将密码明文形式转换成MD5密文
        loginPwd = MD5Util.getMD5(loginPwd);    //直接访问xxx.do此处存在空指针异常

        //接受浏览器ip地址
        String ip = request.getRemoteAddr();
        System.out.println(loginAct + " , " + loginPwd + " , " + ip);

        //未来业务层开发，统一使用代理类形态的接口对象
        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = userService.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user",user);

            //如果程序执行到此处，表示登录成功
            //使用工具类 返回 {"success":true}
            PrintJson.printJsonFlag(response,true);
        } catch (Exception e){
            /*
            返回 {"success":true,"msg":?}
            */
            String msg = e.getMessage();
            /*
            我们现在作为contoller，需要为ajax请求提供多项信息
            可以有两种手段来处理：
                    （1）将多项信息打包成为map，将map解析为json串
                    （2）创建一个Vo
                           private boolean success;
                           private String msg;

            如果对于展现的信息将来还会大量的使用，我们创建一个vo类，使用方便
            如果对于展现的信息只有在这个需求中能够使用，我们使用map就可以了*/

            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response, map);
        }

    }
}
