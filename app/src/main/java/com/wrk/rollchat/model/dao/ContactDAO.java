package com.wrk.rollchat.model.dao;

import com.wrk.rollchat.greenDAO.ContactInfoDao;
import com.wrk.rollchat.model.bean.ContactInfo;
import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.model.db.DBManager;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 联系人操作类
 * -------------------=.=------------------------
 */

public class ContactDAO {

    private ContactInfoDao mContactInfoDao;

    public ContactDAO(DBManager dbManager) {
        mContactInfoDao = dbManager.getContactInfoDao();
    }

    // 获取所有联系人
    public List<ContactInfo> getContacts() {
        QueryBuilder<ContactInfo> queryBuilder = mContactInfoDao.queryBuilder();
        queryBuilder.where(ContactInfoDao.Properties.Is_contact.eq(1));
        return queryBuilder.list();
    }

    // 通过环信id获取单个联系人信息
    public ContactInfo getContactByHxid(String hxid) {
        if (hxid == null) {
            return null;
        }
        QueryBuilder<ContactInfo> queryBuilder = mContactInfoDao.queryBuilder();
        queryBuilder.where(ContactInfoDao.Properties.Hxid.eq(hxid));
        if (queryBuilder.list().isEmpty()) {
            return null;
        } else {
            return queryBuilder.list().get(0);
        }
    }

    // 通过环信id获取用户联系人信息
    public List<ContactInfo> getContactsByHxid(List<String> hxids) {

        if (hxids == null || hxids.size() <= 0) {
            return null;
        }

        QueryBuilder<ContactInfo> queryBuilder = mContactInfoDao.queryBuilder();
        queryBuilder.where(ContactInfoDao.Properties.Hxid.in(hxids));
        return queryBuilder.list();
    }

    // 保存单个联系人
    public void saveContact(UserInfo userInfo, boolean isMyContact) {

        if (userInfo == null) {
            return;
        }

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setHxid(userInfo.getHxid());
        contactInfo.setName(userInfo.getName());
        contactInfo.setNick(userInfo.getNick());
        contactInfo.setPhoto(userInfo.getPhoto());
        contactInfo.setIs_contact(isMyContact ? 1 : 0);

        mContactInfoDao.insertOrReplace(contactInfo);
    }


    // 保存联系人列表
    public void saveContacts(List<UserInfo> contacts, boolean isMyContact) {
        if (contacts == null || contacts.size() <= 0) {
            return;
        }

        for (UserInfo contact : contacts) {
            saveContact(contact, isMyContact);
        }
    }

    // 删除联系人
    public void deleteContactByHxid(String hxid) {
        if (hxid == null) {
            return;
        }

        mContactInfoDao.deleteByKey(hxid);

    }

}





















