package com.wrk.rollchat.model.db;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.model.bean.InvitationInfo;
import com.wrk.rollchat.model.bean.InvitationStatus;
import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.utils.Constant;
import com.wrk.rollchat.utils.SpUtils;

/**
 * Created by MrbigW on 2016/11/5.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 全局监听
 * -------------------=.=------------------------
 */

public class EventListener {

    private Context mContext;
    private final LocalBroadcastManager mBroadcastManager;

    public EventListener(Context context) {
        mContext = context;

        // 联系人的全局监听
        EMClient.getInstance().contactManager().setContactListener(mEMContactListener);
        // 本地广播管理者
        mBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    private final EMContactListener mEMContactListener = new EMContactListener() {
        /**
         * 联系人增加了
         * @param hxid
         */
        @Override
        public void onContactAdded(String hxid) {
            // 本地
            Model.getInstance().getInCoManager().getContactDAO().saveContact(new UserInfo(hxid), true);
            // 发送联系人改变广播
            mBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        /**
         * 联系人删除了
         * @param hxid
         */
        @Override
        public void onContactDeleted(String hxid) {
            // 本地
            Model.getInstance().getInCoManager().getContactDAO().deleteContactByHxid(hxid);
            // 发送联系人改变广播
            mBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        /**
         * 接受到好友邀请
         * @param hxid
         * @param reason
         */
        @Override
        public void onContactInvited(String hxid, String reason) {
            InvitationInfo info = new InvitationInfo();
            info.setReason(reason);
            info.setUser_hxid(hxid);
            info.setStatus(InvitationStatus.NEW_INVITE);
            Model.getInstance().getInCoManager().getInvitationDAO().addInvitation(info);

            // 红点处理
            SpUtils.getInstace().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请广播
            mBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        /**
         * 邀请被接受了
         * @param hxid
         */
        @Override
        public void onContactAgreed(String hxid) {
            // 本地数据变化
            InvitationInfo info = new InvitationInfo();
            info.setUser_hxid(hxid);
            info.setStatus(InvitationStatus.INVITE_ACCEPT_BY_PEER);
            Model.getInstance().getInCoManager().getInvitationDAO().addInvitation(info);

            // 红点处理
            SpUtils.getInstace().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请广播
            mBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));

        }

        /**
         * 邀请被拒绝
         * @param hxid
         */
        @Override
        public void onContactRefused(String hxid) {
            // 红点处理
            SpUtils.getInstace().save(SpUtils.IS_NEW_INVITE, true);

            // 发送邀请广播
            mBroadcastManager.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
}


































