package com.weiweicode.crm.workbench.service;

import com.weiweicode.crm.workbench.pojo.Tran;
import com.weiweicode.crm.workbench.pojo.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/31 16:34
 * @Version 1.0
 */
public interface TranService {
    boolean save(Tran t, String customerName);

    Tran detail(String id);

    List<TranHistory> getHistoryListByTranId(String tranId);

    boolean changeStage(Tran t);

    Map<String, Object> getCharts();
}
