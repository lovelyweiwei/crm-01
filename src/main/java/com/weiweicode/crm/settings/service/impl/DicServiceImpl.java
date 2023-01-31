package com.weiweicode.crm.settings.service.impl;

import com.weiweicode.crm.settings.mapper.DicTypeMapper;
import com.weiweicode.crm.settings.mapper.DicValueMapper;
import com.weiweicode.crm.settings.pojo.DicType;
import com.weiweicode.crm.settings.pojo.DicValue;
import com.weiweicode.crm.settings.service.DicService;
import com.weiweicode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author weiwei
 * @Date 2023/1/29 21:43
 * @Version 1.0
 */
public class DicServiceImpl implements DicService {

    private DicTypeMapper dicTypeMapper = SqlSessionUtil.getSqlSession().getMapper(DicTypeMapper.class);
    private DicValueMapper dicValueMapper = SqlSessionUtil.getSqlSession().getMapper(DicValueMapper.class);


    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<>();

        //取出字典类型列表
        List<DicType> dtList = dicTypeMapper.getTypeList();

        //遍历字典类型列表
        for (DicType dt : dtList){
            //取得每一种类型的字典类型编码
            String code = dt.getCode();
            //根据每一个字典类型获得字典值列表
            List<DicValue> dvList = dicValueMapper.getListByCode(code);
            //存入map
            map.put(code + "List", dvList);
        }

        return map;
    }
}
