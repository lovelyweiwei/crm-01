package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.TranHistory;

import java.util.List;

public interface TranHistoryMapper {

    int save(TranHistory tranHistory);

    List<TranHistory> getHistoryListByTranId(String tranId);
}
