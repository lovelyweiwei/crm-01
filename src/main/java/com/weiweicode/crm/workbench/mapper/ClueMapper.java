package com.weiweicode.crm.workbench.mapper;


import com.weiweicode.crm.workbench.pojo.Clue;

public interface ClueMapper {

    int save(Clue c);

    Clue detail(String id);

    Clue getById(String clueId);

    int delete(String clueId);
}
