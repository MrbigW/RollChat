package com.wrk.rollchat.model;

import android.content.Context;

import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.model.dao.InvitationContactManager;
import com.wrk.rollchat.model.dao.UserAccountDAO;
import com.wrk.rollchat.model.db.EventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MrbigW on 2016/11/3.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: toast类
 * -------------------=.=------------------------
 */


public class Model {

    private static Model instance;
    private Context mContext;
    private UserAccountDAO mAccountDAO;
    private InvitationContactManager mManager;


    // 创建全局线程池
    public ExecutorService mExecutorService = Executors.newCachedThreadPool();

    private Model() {
    }

    public static synchronized Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    // 初始化数据
    public void init(Context context) {
        this.mContext = context;
        // 初始化用户账号数据库操作类
        mAccountDAO = new UserAccountDAO(context);
        // 初始化全局联系人监听
        EventListener eventListener = new EventListener(context);
    }

    // 获取全局线程池
    public ExecutorService getGlobalThreadPool() {
        return mExecutorService;
    }

    // 获取UserAccountDAO
    public UserAccountDAO getAccountDAO() {
        return mAccountDAO;
    }

    public void loginSuccess(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        if (mManager != null) {
            mManager.close();
        }
        mManager = new InvitationContactManager(mContext, userInfo.getName());
    }

    /**
     * 获取DBManager
     *
     * @return
     */
    public InvitationContactManager getInCoManager() {
        return mManager;
    }
}



























