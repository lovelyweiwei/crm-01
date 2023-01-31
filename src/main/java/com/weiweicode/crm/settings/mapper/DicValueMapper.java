package com.weiweicode.crm.settings.mapper;

import com.weiweicode.crm.settings.pojo.DicValue;

import java.util.List;

/**
 * @Author weiwei
 * @Date 2023/1/29 21:40
 * @Version 1.0
 */
public interface DicValueMapper {
    List<DicValue> getListByCode(String code);
}
