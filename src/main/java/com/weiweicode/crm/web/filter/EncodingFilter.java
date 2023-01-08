package com.weiweicode.crm.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @Author weiwei
 * @Date 2023/1/5 1:26
 * @Version 1.0
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        // System.out.println("编码过滤器执行");

        //过滤post请求中文参数乱码
        servletRequest.setCharacterEncoding("UTF-8");
        //过滤响应流响应中文乱码
        servletResponse.setContentType("text/html;charset=utf-8");

        //将请求放行
        chain.doFilter(servletRequest, servletResponse);
    }
}
