package com.weiweicode.crm.workbench.service.impl;

import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.workbench.mapper.TranHistoryMapper;
import com.weiweicode.crm.workbench.mapper.TranMapper;
import com.weiweicode.crm.workbench.pojo.TranHistory;
import com.weiweicode.crm.workbench.service.TranService;

/**
 * @Author weiwei
 * @Date 2023/1/31 16:34
 * @Version 1.0
 */
public class TranServiceImpl implements TranService {

    private TranMapper tranMapper = SqlSessionUtil.getSqlSession().getMapper(TranMapper.class);
    private TranHistoryMapper tranHistoryMapper = SqlSessionUtil.getSqlSession().getMapper(TranHistoryMapper.class);

}
