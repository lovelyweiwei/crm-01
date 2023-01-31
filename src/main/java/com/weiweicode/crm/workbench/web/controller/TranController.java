package com.weiweicode.crm.workbench.web.controller;

import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.settings.service.impl.UserServiceImpl;
import com.weiweicode.crm.utils.PrintJson;
import com.weiweicode.crm.utils.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.ServerError;
import java.util.List;

/**
 * @Author weiwei
 * @Date 2023/1/31 16:36
 * @Version 1.0
 */
@WebServlet({"/workbench/transaction/add.do"})
public class TranController extends HttpServlet{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入交易控制器");

        String servletPath = request.getServletPath();

        if ("/workbench/transaction/add.do".equals(servletPath)){
            add(request,response);
        } else if ("/workbench/transaction/save.do".equals(servletPath)){
           // xx(request,response);
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到跳转到交易添加页的操作");

        UserService us = ((UserService) ServiceFactory.getService(new UserServiceImpl()));

        List<User> userList = us.getUserList();

        request.setAttribute("userList",userList);

        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}
