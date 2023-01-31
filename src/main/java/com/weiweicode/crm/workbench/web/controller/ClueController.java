package com.weiweicode.crm.workbench.web.controller;

import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.settings.service.impl.UserServiceImpl;
import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.PrintJson;
import com.weiweicode.crm.utils.ServiceFactory;
import com.weiweicode.crm.utils.UUIDUtil;
import com.weiweicode.crm.workbench.pojo.Activity;
import com.weiweicode.crm.workbench.pojo.Clue;
import com.weiweicode.crm.workbench.pojo.Tran;
import com.weiweicode.crm.workbench.service.ActivityService;
import com.weiweicode.crm.workbench.service.ClueService;
import com.weiweicode.crm.workbench.service.impl.ActivityServiceImpl;
import com.weiweicode.crm.workbench.service.impl.ClueServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author weiwei
 * @Date 2023/1/29 21:31
 * @Version 1.0
 */
@WebServlet({"/workbench/clue/getUserList.do","/workbench/clue/save.do",
        "/workbench/clue/detail.do","/workbench/clue/getActivityListByClueId.do",
        "/workbench/clue/unbund.do","/workbench/clue/getActivityListByNameAndNotByClueId.do",
        "/workbench/clue/bund.do","/workbench/clue/getActivityListByName.do",
        "/workbench/clue/convert.do"})
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入线索控制器");

        String servletPath = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(servletPath)){
            getUserList(request,response);
        } else if ("/workbench/clue/save.do".equals(servletPath)){
            save(request,response);
        } else if ("/workbench/clue/detail.do".equals(servletPath)) {
            detail(request,response);
        } else if ("/workbench/clue/getActivityListByClueId.do".equals(servletPath)){
            getActivityListByClueId(request,response);
        } else if ("/workbench/clue/unbund.do".equals(servletPath)){
            unbund(request,response);
        } else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(servletPath)){
            getActivityListByNameAndNotByClueId(request,response);
        } else if ("/workbench/clue/bund.do".equals(servletPath)) {
            bund(request,response);
        } else if ("/workbench/clue/getActivityListByName.do".equals(servletPath)){
            getActivityListByName(request,response);
        } else if ("/workbench/clue/convert.do".equals(servletPath)){
            convert(request,response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("执行线索转换操作");

        String clueId = request.getParameter("clueId");

        String flag = request.getParameter("flag");

        Tran t = null;
        String createBy = null;


        //判断是否需要交易
        if ("0".equals(flag)) {

            t = new Tran();

            //接受参数
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("ac~tivityId");
            String id = UUIDUtil.getUUID();
            String createTime = DateTimeUtil.getSysTime();
            createBy = ((User) request.getSession().getAttribute("user")).getName();

            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setId(id);
            t.setCreateTime(createTime);
            t.setCreateBy(createBy);
        }

        ClueService cs = ((ClueService) ServiceFactory.getService(new ClueServiceImpl()));

        boolean flag1 = cs.convert(clueId, t, createBy);

        if (flag1) {
            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
        }

        PrintJson.printJsonFlag(response,flag1);
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("查询市场活动列表（根据名称模糊查询）");

        String aname = request.getParameter("aname");

        ActivityService as = ((ActivityService) ServiceFactory.getService(new ActivityServiceImpl()));

        List<Activity> aList = as.getActivityListByName(aname);

        PrintJson.printJsonObj(response, aList);

    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行关联操作");

        String cid = request.getParameter("cid");
        String[] aids = request.getParameterValues("aid");

        ClueService cs = ((ClueService) ServiceFactory.getService(new ClueServiceImpl()));

        boolean flag = cs.bund(cid,aids);

        PrintJson.printJsonFlag(response, flag);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行解除关联操作");

        String id = request.getParameter("id");

        ClueService cs = ((ClueService) ServiceFactory.getService(new ClueServiceImpl()));

        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(response, flag);
    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行查询市场活动信息操作（根据名称模糊查询）");

        String activityName = request.getParameter("activityName");
        String clueId = request.getParameter("clueId");

        Map<String, String> map = new HashMap<>();
        map.put("aname",activityName);
        map.put("clueId",clueId);

        ActivityService as = ((ActivityService) ServiceFactory.getService(new ActivityServiceImpl()));

        List<Activity> aList = as.getActivityListByNameAndNotByClueId(map);

        PrintJson.printJsonObj(response, aList);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据线索id查询关联市场活动列表");

        String clueId = request.getParameter("clueId");

        ActivityService as = ((ActivityService) ServiceFactory.getService(new ActivityServiceImpl()));

        List<Activity> aList = as.getActivityListByClueId(clueId);

        PrintJson.printJsonObj(response, aList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入线索详情页面");

        String id = request.getParameter("id");

        ClueService cs = ((ClueService) ServiceFactory.getService(new ClueServiceImpl()));

        Clue c = cs.detail(id);

        request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行线索添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue c = new Clue();
        c.setId(id);
        c.setAddress(address);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);

        ClueService cs = ((ClueService) ServiceFactory.getService(new ClueServiceImpl()));

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService userService = ((UserService) ServiceFactory.getService(new UserServiceImpl()));

        List<User> userList = userService.getUserList();

        PrintJson.printJsonObj(response, userList);
    }

}
