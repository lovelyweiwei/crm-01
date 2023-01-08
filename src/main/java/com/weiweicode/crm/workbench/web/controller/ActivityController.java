package com.weiweicode.crm.workbench.web.controller;

import com.weiweicode.crm.settings.pojo.User;
import com.weiweicode.crm.settings.service.UserService;
import com.weiweicode.crm.settings.service.impl.UserServiceImpl;
import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.PrintJson;
import com.weiweicode.crm.utils.ServiceFactory;
import com.weiweicode.crm.utils.UUIDUtil;
import com.weiweicode.crm.vo.PaginationVO;
import com.weiweicode.crm.workbench.pojo.Activity;
import com.weiweicode.crm.workbench.service.ActivityService;
import com.weiweicode.crm.workbench.service.impl.ActivityServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sun.swing.StringUIClientPropertyKey;

import javax.xml.ws.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author weiwei
 * @Date 2023/1/5 23:19
 * @Version 1.0
 */
@WebServlet({"/workbench/activity/getUserList.do","/workbench/activity/save.do","/workbench/activity/pageList.do"})
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");

        String servletPath = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(servletPath)){
            getUserList(request,response);
        } else if ("/workbench/activity/save.do".equals(servletPath)){
            save(request,response);
        } else if ("/workbench/activity/pageList.do".equals(servletPath)){
            pageList(request,response);
        }
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入查询市场活动信息列表的操作");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",(pageNo - 1) * pageSize);
        map.put("pageSize",pageSize);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*
            前端要： 市场活动信息列表
                    查询的总条数

                    业务层拿到了以上两项信息之后，如果做返回
                    map
                    map.put("dataList":dataList)
                    map.put("total":total)
                    PrintJSON map --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}

                    vo
                    PaginationVO<T>
                        private int total;
                        private List<T> dataList;

                    PaginationVO<Activity> vo = new PaginationVO<>;
                    vo.setTotal(total);
                    vo.setDataList(dataList);
                    PrintJSON vo --> json
                    {"total":100,"dataList":[{市场活动1},{2},{3}]}

                    将来分页查询，每个模块都有，所以我们选择使用一个通用vo，操作起来比较方便
         */
        PaginationVO<Activity> vo = activityService.pageList(map);

        PrintJson.printJsonObj(response, vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        //创建事件，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人，当前登录用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);;
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = activityService.save(activity);

        PrintJson.printJsonFlag(response, flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("取得用户信息列表");

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> userList =  userService.getUserList();

        PrintJson.printJsonObj(response, userList);
    }

}
