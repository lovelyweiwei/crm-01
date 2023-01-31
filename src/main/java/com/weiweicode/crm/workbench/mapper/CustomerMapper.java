package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.Customer;

import java.util.List;

public interface CustomerMapper {

    Customer getCustomerByName(String company);

    int save(Customer cus);

    List<String> getCustomerName(String name);
}
