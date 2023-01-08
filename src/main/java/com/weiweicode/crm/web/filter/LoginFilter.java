package com.weiweicode.crm.web.filter;

import com.weiweicode.crm.settings.pojo.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @Author weiwei
 * @Date 2023/1/5 2:00
 * @Version 1.0
 */
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();
        HttpSession session = request.getSession();


        //不应该被拦截的资源，自动放行请求
        if ("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath) ||
                session.getAttribute("user") != null){
            chain.doFilter(request,response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }
}
