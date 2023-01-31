package com.weiweicode.crm.workbench.service.impl;

import com.weiweicode.crm.utils.DateTimeUtil;
import com.weiweicode.crm.utils.SqlSessionUtil;
import com.weiweicode.crm.utils.UUIDUtil;
import com.weiweicode.crm.workbench.mapper.*;
import com.weiweicode.crm.workbench.pojo.*;
import com.weiweicode.crm.workbench.service.ClueService;

import java.util.List;

/**
 * @Author weiwei
 * @Date 2023/1/29 21:28
 * @Version 1.0
 */
public class ClueServiceImpl implements ClueService {

    private ClueMapper clueMapper = SqlSessionUtil.getSqlSession().getMapper(ClueMapper.class);
    private ClueActivityRelationMapper clueActivityRelationMapper = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationMapper.class);
    private ClueRemarkMapper clueRemarkMapper = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkMapper.class);

    private CustomerMapper customerMapper = SqlSessionUtil.getSqlSession().getMapper(CustomerMapper.class);
    private CustomerRemarkMapper customerRemarkMapper = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkMapper.class);

    private ContactsMapper contactsMapper = SqlSessionUtil.getSqlSession().getMapper(ContactsMapper.class);
    private ContactsRemarkMapper contactsRemarkMapper = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkMapper.class);
    private ContactsActivityRelationMapper contactsActivityRelationMapper = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationMapper.class);

    private TranMapper tranMapper = SqlSessionUtil.getSqlSession().getMapper(TranMapper.class);
    private TranHistoryMapper tranHistoryMapper = SqlSessionUtil.getSqlSession().getMapper(TranHistoryMapper.class);


    @Override
    public boolean save(Clue c) {

        boolean flag = true;

        int count = clueMapper.save(c);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {

        Clue c = clueMapper.detail(id);

        return c;
    }

    @Override
    public boolean unbund(String id) {

        boolean flag = true;

        int count = clueActivityRelationMapper.unbund(id);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean bund(String cid, String[] aids) {
        boolean flag = true;

        for (String aid : aids) {
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(aid);
            car.setClueId(cid);

            int count = clueActivityRelationMapper.bund(car);

            if (count != 1) {
                flag = false;
            }
        }

        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran t, String createBy) {

        String creatTime = DateTimeUtil.getSysTime();

        boolean flag = true;

        //(1)通过线索id获取线索
        Clue c = clueMapper.getById(clueId);

        //(2)通过线索对象提取客户信息，当该客户信息不存在，新建客户
        String company = c.getCompany();
        Customer cus = customerMapper.getCustomerByName(company);
        //如果cus为空，新建客户
        if (cus == null){
            cus = new Customer();
            cus.setId(UUIDUtil.getUUID());
            cus.setAddress(c.getAddress());
            cus.setWebsite(c.getWebsite());
            cus.setOwner(c.getOwner());
            cus.setPhone(c.getPhone());
            cus.setNextContactTime(c.getNextContactTime());
            cus.setContactSummary(c.getContactSummary());
            cus.setName(company);
            cus.setDescription(c.getDescription());
            cus.setCreateBy(c.getCreateBy());
            cus.setCreateTime(c.getCreateTime());

            //添加客户
            int count1 = customerMapper.save(cus);
            if (count1 != 1){
                flag = false;
            }
        }

        //(3)通过线索对象提取联系人信息
        Contacts con = new Contacts();
        con.setId(UUIDUtil.getUUID());
        con.setSource(c.getSource());
        con.setOwner(c.getOwner());
        con.setNextContactTime(c.getNextContactTime());
        con.setMphone(c.getMphone());
        con.setJob(c.getJob());
        con.setFullname(c.getFullname());
        con.setEmail(c.getEmail());
        con.setDescription(c.getDescription());
        con.setCreateBy(c.getCreateBy());
        con.setCreateTime(c.getCreateTime());
        con.setContactSummary(c.getContactSummary());
        con.setAppellation(c.getAppellation());
        con.setAddress(c.getAddress());
        con.setCustomerId(cus.getId());

        //添加联系人
        int count2 = contactsMapper.save(con);
        if (count2 != 1){
            flag = false;
        }

        //(4)将线索备注转换为客户备注和联系人备注
        //查询线索备注关联的备注信息列表
        List<ClueRemark> clueRemarkList = clueRemarkMapper.getListByClueId(clueId);
        for (ClueRemark clueRemark : clueRemarkList){

            //取出每一条线索备注信息
            String noteContent = clueRemark.getNoteContent();

            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(creatTime);
            customerRemark.setCustomerId(cus.getId());
            customerRemark.setEditFlag("0");

            int count3 = customerRemarkMapper.save(customerRemark);
            if (count3 != 1){
                flag = false;
            }

            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setContactsId(con.getId());
            contactsRemark.setCreateTime(creatTime);
            contactsRemark.setEditFlag("0");

            int count4 = contactsRemarkMapper.save(contactsRemark);
            if (count4 != 1){
                flag = false;
            }
        }

        //(5)将“线索与活动”的关系转换到“联系人与市场活动”的关系
        //查询出与该条线索相关的市场活动
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.getListByClueId(clueId);
        //遍历取出每一条线索关联记录
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            String activityId = clueActivityRelation.getActivityId();

            //联系人与市场活动关联
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(con.getId());

            int count5 = contactsActivityRelationMapper.save(contactsActivityRelation);
            if (count5 != 1){
                flag = false;
            }

        }

        //(6)如果需要创建交易，就创建交易
        if (t != null){
            //t对象已经封装好了一些对象
            t.setSource(c.getSource());
            t.setOwner(c.getOwner());
            t.setNextContactTime(c.getNextContactTime());
            t.setDescription(c.getDescription());
            t.setCustomerId(cus.getId());
            t.setContactSummary(c.getContactSummary());
            t.setContactsId(con.getId());
            //添加交易
            int count6 = tranMapper.save(t);
            if (count6 != 1){
                flag = false;
            }

            //(7)如果创建了交易，就创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(creatTime);
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setStage(t.getStage());
            tranHistory.setTranId(t.getId());

            int count7 = tranHistoryMapper.save(tranHistory);
            if (count7 != 1){
                flag = false;
            }
        }

        //（8）删除线索备注
        for (ClueRemark clueRemark : clueRemarkList){
            int count8 = clueRemarkMapper.delete(clueRemark);
            if (count8 != 1){
                flag = false;
            }
        }

        //（9）删除线索和市场活动的关系
        for (ClueActivityRelation clueActivityRelation: clueActivityRelationList){
            int count9 = clueActivityRelationMapper.delete(clueActivityRelation);
            if (count9 != 1){
                flag = false;
            }
        }

        //（10）删除线索和市场活动的关系
        int count10 = clueMapper.delete(clueId);
        if (count10 != 1){
            flag = false;
        }

        return flag;
    }
}

