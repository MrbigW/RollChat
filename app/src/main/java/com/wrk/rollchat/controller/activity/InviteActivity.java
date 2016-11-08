package com.wrk.rollchat.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wrk.rollchat.R;
import com.wrk.rollchat.controller.adapter.InviteAdapter;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.model.bean.InvitationInfo;
import com.wrk.rollchat.model.bean.InvitationStatus;
import com.wrk.rollchat.utils.Constant;
import com.wrk.rollchat.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends AppCompatActivity {

    @BindView(R.id.lv_invite)
    ListView lvInvite;

    private LocalBroadcastManager mManager;
    private BroadcastReceiver InviteChangedReceviver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refresh();
        }
    };


    private InviteAdapter mAdapter;

    private InviteAdapter.onInviteChangedListener mOnInviteChangedListener = new InviteAdapter.onInviteChangedListener() {
        /**
         * 接受联系人邀请
         * @param invitationInfo
         */
        @Override
        public void onAccept(InvitationInfo invitationInfo) {
            final String hxid = invitationInfo.getUser_hxid();
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        // 网络
                        EMClient.getInstance().contactManager().acceptInvitation(hxid);
                        // 本地
                        Model.getInstance().getInCoManager().getInvitationDAO().updateInvitationStatus(InvitationStatus.INVITE_ACCEPT, hxid);
                        // 内存
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 刷新页面
                                refresh();
                                ToastUtil.showToast(InviteActivity.this, "接受成功");
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }

                }
            });


        }

        /**
         * 拒绝联系人邀请
         * @param invitationInfo
         */
        @Override
        public void onReject(InvitationInfo invitationInfo) {
            final String hxid = invitationInfo.getUser_hxid();
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        // 网络
                        EMClient.getInstance().contactManager().deleteContact(hxid);
                        // 本地
                        Model.getInstance().getInCoManager().getInvitationDAO().removeInvitation(hxid);
                        Model.getInstance().getInCoManager().getContactDAO().deleteContactByHxid(hxid);
                        // 内存及页面
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 刷新
                                refresh();
                                ToastUtil.showToast(InviteActivity.this, "拒绝成功");
                            }
                        });
                    } catch (final HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 刷新
                                ToastUtil.showToast(InviteActivity.this, "拒绝失败，" + e.getMessage());
                            }
                        });
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        mAdapter = new InviteAdapter(this, mOnInviteChangedListener);
        lvInvite.setAdapter(mAdapter);

        // 刷新
        refresh();

        // 注册邀请信息改变的广播
        mManager = LocalBroadcastManager.getInstance(this);
        mManager.registerReceiver(InviteChangedReceviver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));


    }

    private void refresh() {
        // 获取邀请信息
        List<InvitationInfo> invitations = Model.getInstance().getInCoManager().getInvitationDAO().getInvitations();
        Log.e("111", invitations.toString());
        mAdapter.refresh(invitations);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mManager.unregisterReceiver(InviteChangedReceviver);

    }

}
