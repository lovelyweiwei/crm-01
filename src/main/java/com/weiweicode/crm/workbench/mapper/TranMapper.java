package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {

    int save(Tran t);

    Tran detail(String id);

    int changeStage(Tran t);

    int getTotal();

    List<Map<String, Object>> getCharts();
}
