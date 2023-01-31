package com.weiweicode.crm.settings.service;

import com.weiweicode.crm.settings.pojo.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/29 21:43
 * @Version 1.0
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
