package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.Customer;

public interface CustomerMapper {

    Customer getCustomerByName(String company);

    int save(Customer cus);
}
