package com.weiweicode.crm.web.listener;

import com.weiweicode.crm.settings.pojo.DicValue;
import com.weiweicode.crm.settings.service.DicService;
import com.weiweicode.crm.settings.service.impl.DicServiceImpl;
import com.weiweicode.crm.utils.ServiceFactory;
import com.weiweicode.crm.workbench.service.impl.ActivityServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author weiwei
 * @Date 2023/1/30 11:46
 * @Version 1.0
 */
@WebListener
public class SysInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("服务器缓存处理数据字典");

        ServletContext application = sce.getServletContext();

        DicService ds = ((DicService) ServiceFactory.getService(new DicServiceImpl()));

        //取数据字典
        /*
          按类型分别查出 很多个list
          打包成 一个map
         */

        Map<String, List<DicValue>> map = ds.getAll();

        //将map解析为上下文域对象中保存的键值对
        Set<String> set = map.keySet();
        for (String key : set){
            application.setAttribute(key, map.get(key));
        }

    }

}
