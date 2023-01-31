package com.weiweicode.crm.workbench.mapper;

import com.weiweicode.crm.workbench.pojo.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {

    List<ClueRemark> getListByClueId(String clueId);

    int delete(ClueRemark clueRemark);
}
