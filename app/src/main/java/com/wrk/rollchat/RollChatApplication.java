package com.wrk.rollchat;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.wrk.rollchat.model.Model;

/**
 * Created by MrbigW on 2016/11/3.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: app的Application
 * -------------------=.=------------------------
 */

public class RollChatApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化环信EaseUI
        initEaseUI();

        // 初始化Model
        Model.getInstance().init(this);

        this.mContext = this;

    }

    private void initEaseUI() {
        EMOptions options = new EMOptions();
        // 禁用-自动接收群邀请
        options.setAutoAcceptGroupInvitation(false);
        // 禁用-不会接收所有邀请
        options.setAcceptInvitationAlways(false);
        EaseUI.getInstance().init(this, options);
    }

    /**
     * 获取全局上下文
     * @return
     */
    public static Context getAppContext() {
        return mContext;
    }


}













