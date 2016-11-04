package com.wrk.rollchat.model.dao;

import android.content.Context;

import com.wrk.rollchat.greenDAO.UserInfoDao;
import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.model.db.DBManager;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Created by MrbigW on 2016/11/3.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: -.-
 * -------------------=.=------------------------
 */

public class UserAccountDAO {

    private UserInfoDao mUserInfoDao;

    public UserAccountDAO(Context context) {
        mUserInfoDao = new DBManager(context, "account.db").getUserInfoDao();
    }

    /**
     * 添加用户
     *
     * @param userInfo
     */
    public void addAccount(UserInfo userInfo) {
        mUserInfoDao.insertOrReplace(userInfo);
    }

    /**
     * 根据环信id获取所有用户的信息
     *
     * @param hxid
     * @return
     */
    public UserInfo getAccountByHxid(String hxid) {
        QueryBuilder<UserInfo> queryBuilder = mUserInfoDao.queryBuilder();
        queryBuilder.where(UserInfoDao.Properties.Hxid.eq(hxid));
        if(queryBuilder.list().isEmpty()) {
            return  null;
        }else {
            return queryBuilder.list().get(0);
        }
    }

}











































