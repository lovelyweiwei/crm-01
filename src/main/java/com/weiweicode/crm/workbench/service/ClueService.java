package com.weiweicode.crm.workbench.service;

import com.weiweicode.crm.workbench.pojo.Clue;
import com.weiweicode.crm.workbench.pojo.Tran;

/**
 * @Author weiwei
 * @Date 2023/1/29 21:27
 * @Version 1.0
 */
public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);
}
