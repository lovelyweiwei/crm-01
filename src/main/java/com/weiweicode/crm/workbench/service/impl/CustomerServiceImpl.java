package com.weiweicode.crm.workbench.service.impl;

import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.workbench.mapper.CustomerMapper;
import com.weiweicode.crm.workbench.mapper.CustomerRemarkMapper;
import com.weiweicode.crm.workbench.pojo.CustomerRemark;
import com.weiweicode.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * @Author weiwei
 * @Date 2023/1/31 23:11
 * @Version 1.0
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerMapper customerMapper = SqlSessionUtil.getSqlSession().getMapper(CustomerMapper.class);

    @Override
    public List<String> getCustomerName(String name) {

        List<String> cList = customerMapper.getCustomerName(name);

        return cList;
    }
}
