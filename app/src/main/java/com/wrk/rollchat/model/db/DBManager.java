package com.wrk.rollchat.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wrk.rollchat.greenDAO.ContactInfoDao;
import com.wrk.rollchat.greenDAO.DaoMaster;
import com.wrk.rollchat.greenDAO.DaoSession;
import com.wrk.rollchat.greenDAO.InvitationInfoDao;
import com.wrk.rollchat.greenDAO.UserInfoDao;

/**
 * Created by MrbigW on 2016/11/3.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 数据库管理类
 * -------------------=.=------------------------
 */

public class DBManager {

    // 数据库名
    private String name;

    private DaoMaster.DevOpenHelper mOpenHelper;
    private Context mContext;

    public DBManager(Context context, String name) {
        this.mContext = context;
        this.name = name;
        mOpenHelper = new DaoMaster.DevOpenHelper(context, name, null);
    }

    /**
     * 关闭DaoMaster.DevOpenHelper
     */
    public void close() {
        mOpenHelper.close();
    }

    /**
     * 获取可读数据库
     *
     * @return
     */
    private SQLiteDatabase getReadableDatabase() {
        if (mOpenHelper == null) {
            mOpenHelper = new DaoMaster.DevOpenHelper(mContext, name);
        }
        return mOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可xie数据库
     *
     * @return
     */
    private SQLiteDatabase getWritableDatabase() {
        if (mOpenHelper == null) {
            mOpenHelper = new DaoMaster.DevOpenHelper(mContext, name);
        }
        return mOpenHelper.getWritableDatabase();
    }

    public UserInfoDao getUserInfoDao() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getUserInfoDao();
    }

    public ContactInfoDao getContactInfoDao() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getContactInfoDao();
    }

    public InvitationInfoDao getInvitationInfoDao() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getInvitationInfoDao();
    }


}































