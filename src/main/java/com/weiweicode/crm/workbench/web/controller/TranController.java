package com.weiweicode.crm.workbench.web.controller;

import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.settings.service.impl.UserServiceImpl;
import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.PrintJson;
import com.weiweicode.crm.utils.ServiceFactory;
import com.weiweicode.crm.utils.UUIDUtil;
import com.weiweicode.crm.workbench.pojo.Tran;
import com.weiweicode.crm.workbench.pojo.TranHistory;
import com.weiweicode.crm.workbench.service.CustomerService;
import com.weiweicode.crm.workbench.service.TranService;
import com.weiweicode.crm.workbench.service.impl.CustomerServiceImpl;
import com.weiweicode.crm.workbench.service.impl.TranServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/31 16:36
 * @Version 1.0
 */
@WebServlet({"/workbench/transaction/add.do","/workbench/transaction/getCustomerName.do",
        "/workbench/transaction/save.do","/workbench/transaction/detail.do",
        "/workbench/transaction/getHistoryListByTranId.do","/workbench/transaction/changeStage.do",
        "/workbench/transaction/getCharts.do"})
public class TranController extends HttpServlet{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入交易控制器");

        String servletPath = request.getServletPath();

        if ("/workbench/transaction/add.do".equals(servletPath)){
            add(request,response);
        } else if ("/workbench/transaction/getCustomerName.do".equals(servletPath)){
           getCustomerName(request,response);
        } else if ("/workbench/transaction/save.do".equals(servletPath)) {
            save(request,response);
        } else if ("/workbench/transaction/detail.do".equals(servletPath)) {
            detail(request,response);
        } else if ("/workbench/transaction/getHistoryListByTranId.do".equals(servletPath)) {
            getHistoryListByTranId(request,response);
        } else if ("/workbench/transaction/changeStage.do".equals(servletPath)) {
            changeStage(request,response);
        } else if ("/workbench/transaction/getCharts.do".equals(servletPath)) {
            getCharts(request,response);
        }
    }

    private void getCharts(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得交易阶段数量统计图表数据");

        TranService ts = ((TranService) ServiceFactory.getService(new TranServiceImpl()));

        Map<String,Object> map = ts.getCharts();

        PrintJson.printJsonObj(response,map);

    }

    private void changeStage(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行改变阶段操作");

        String id = request.getParameter("id");
        String stage = request.getParameter("stage");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Tran t = new Tran();
        t.setId(id);
        t.setStage(stage);
        t.setMoney(money);
        t.setExpectedDate(expectedDate);
        t.setEditBy(editBy);
        t.setEditTime(editTime);

        TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());

        boolean flag = ts.changeStage(t);

        //处理可能性
        Map<String, String> pMap = (Map<String, String>)this.getServletContext().getAttribute("pMap");
        t.setPossibility(pMap.get(stage));

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("t",t);

        PrintJson.printJsonObj(response, map);
    }

    private void getHistoryListByTranId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据交易id取得对应的历史列表");

        String tranId = request.getParameter("tranId");

        TranService ts = (TranService)ServiceFactory.getService(new TranServiceImpl());

        List<TranHistory> th = ts.getHistoryListByTranId(tranId);

        //处理可能性
        ServletContext application = this.getServletContext();
        Map<String, String> pMap = (Map<String, String>)application.getAttribute("pMap");

        //将交易历史列表遍历
        for (TranHistory tranHistory : th){

            //取出每一条历史的阶段stage
            String stage = tranHistory.getStage();
            tranHistory.setPossibility(pMap.get(stage));

        }

        PrintJson.printJsonObj(response, th);

    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("执行到跳转到详情页操作");

        String id = request.getParameter("id");

        TranService ts = (TranService) ServiceFactory.getService(new TranServiceImpl());

        Tran t = ts.detail(id);

        /*
            处理 阶段和可能性之间的关系
         */
        String stage = t.getStage();
        ServletContext application = this.getServletContext();
        Map<String, String> pMap = (Map<String, String>)application.getAttribute("pMap");
        String possibility = pMap.get(stage);
        t.setPossibility(possibility);

        request.setAttribute("t", t);

        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("执行添加交易操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String money = request.getParameter("money");
        String name = request.getParameter("name");
        String expectedDate = request.getParameter("expectedDate");
        String customerName = request.getParameter("customerName"); // 这里暂时只有客户名称，没有客户id
        String stage = request.getParameter("stage");
        String type = request.getParameter("type");
        String source = request.getParameter("source");
        String activityId = request.getParameter("activityId");
        String contactsId = request.getParameter("contactsId");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");

        Tran t = new Tran();
        t.setType(type);
        t.setMoney(money);
        t.setContactsId(contactsId);
        t.setContactSummary(contactSummary);
        t.setDescription(description);
        t.setNextContactTime(nextContactTime);
        t.setOwner(owner);
        t.setSource(source);
        t.setCreateBy(createBy);
        t.setCreateTime(createTime);
        t.setId(id);
        t.setActivityId(activityId);
        t.setStage(stage);
        t.setExpectedDate(expectedDate);
        t.setName(name);

        TranService ts = ((TranService) ServiceFactory.getService(new TranServiceImpl()));

        boolean flag = ts.save(t,customerName);

        if (flag){
            //重定向到index页
            response.sendRedirect(request.getContextPath() + "/workbench/transaction/index.jsp");
        }

    }

    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得客户名称列表（按照客户名称进行模糊查询）");

        String name = request.getParameter("name");

        CustomerService cs = ((CustomerService) ServiceFactory.getService(new CustomerServiceImpl()));

        List<String> customerList = cs.getCustomerName(name);

        PrintJson.printJsonObj(response, customerList);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到跳转到交易添加页的操作");

        UserService us = ((UserService) ServiceFactory.getService(new UserServiceImpl()));

        List<User> userList = us.getUserList();

        request.setAttribute("userList",userList);

        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }
}
