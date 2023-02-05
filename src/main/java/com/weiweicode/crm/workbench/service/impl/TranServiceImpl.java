package com.weiweicode.crm.workbench.service.impl;

import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.utils.UUIDUtil;
import com.weiweicode.crm.workbench.mapper.CustomerMapper;
import com.weiweicode.crm.workbench.mapper.TranHistoryMapper;
import com.weiweicode.crm.workbench.mapper.TranMapper;
import com.weiweicode.crm.workbench.pojo.Customer;
import com.weiweicode.crm.workbench.pojo.Tran;
import com.weiweicode.crm.workbench.pojo.TranHistory;
import com.weiweicode.crm.workbench.service.TranService;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/31 16:34
 * @Version 1.0
 */
public class TranServiceImpl implements TranService {

    private TranMapper tranMapper = SqlSessionUtil.getSqlSession().getMapper(TranMapper.class);
    private TranHistoryMapper tranHistoryMapper = SqlSessionUtil.getSqlSession().getMapper(TranHistoryMapper.class);

    private CustomerMapper customerMapper = SqlSessionUtil.getSqlSession().getMapper(CustomerMapper.class);

    @Override
    public boolean save(Tran t, String customerName) {

        boolean flag = true;

        //判断客户名称在客户表精确查询，如果有，则取出这个客户的id封装到t对象
        //如果没有，则新建一个客户，并将客户id封装到t
        Customer customer = customerMapper.getCustomerByName(customerName);

        //如果不存在，则新建一个客户
        if (customer == null){

            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setName(customerName);
            customer.setOwner(t.getOwner());
            customer.setCreateTime(t.getCreateTime());
            customer.setCreateBy(t.getCreateBy());
            customer.setContactSummary(t.getContactSummary());
            customer.setNextContactTime(t.getNextContactTime());
            //保存新建客户
            int count1 = customerMapper.save(customer);
            if (count1 != 1){
                flag = false;
            }
        }

        t.setCustomerId(customer.getId());

        int count2 = tranMapper.save(t);
        if (count2 != 1){
            flag = false;
        }

        //添加交易历史

        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setTranId(t.getId());
        th.setStage(t.getStage());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setCreateBy(t.getCreateBy());

        int count3 = tranHistoryMapper.save(th);
        if (count3 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran detail(String id) {

        Tran t = tranMapper.detail(id);

        return t;
    }

    @Override
    public List<TranHistory> getHistoryListByTranId(String tranId) {

        List<TranHistory> thList = tranHistoryMapper.getHistoryListByTranId(tranId);

        return thList;
    }

    @Override
    public boolean changeStage(Tran t) {

        boolean flag = true;

        //修改交易阶段
        int count1 = tranMapper.changeStage(t);
        if (count1 != 1){
            flag = false;
        }

        //交易阶段改变后，生成一条交易历史
        TranHistory th = new TranHistory();
        th.setId(UUIDUtil.getUUID());
        th.setCreateBy(t.getEditBy());
        th.setCreateTime(DateTimeUtil.getSysTime());
        th.setMoney(t.getMoney());
        th.setExpectedDate(t.getExpectedDate());
        th.setTranId(t.getId());
        th.setStage(t.getStage());

        int count2 = tranHistoryMapper.save(th);
        if (count2 != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getCharts() {

        Map<String, Object> map = new HashMap<>();

        //取得total
        int total = tranMapper.getTotal();

        //取得dataList
        List<Map<String,Object>> dataList = tranMapper.getCharts();

        map.put("total",total);
        map.put("dataList",dataList);

        return map;
    }
}
