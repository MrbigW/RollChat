package com.wrk.rollchat.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.wrk.rollchat.R;
import com.wrk.rollchat.RollChatApplication;
import com.wrk.rollchat.controller.activity.AddContactActivity;
import com.wrk.rollchat.controller.activity.ChatActivity;
import com.wrk.rollchat.controller.activity.InviteActivity;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.model.bean.ContactInfo;
import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.utils.Constant;
import com.wrk.rollchat.utils.SpUtils;
import com.wrk.rollchat.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 联系人列表页面
 * -------------------=.=------------------------
 */

public class ContactListFragment extends EaseContactListFragment {

    private ImageView iv_contactlist_red;
    private LinearLayout ll_contact_invite;
    private String mHxid;
    // 本地广播管理者
    private LocalBroadcastManager mBroadcastManager;

    private BroadcastReceiver InviteChangedRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 刷新页面
            iv_contactlist_red.setVisibility(View.VISIBLE);
            SpUtils.getInstace().save(SpUtils.IS_NEW_INVITE, true);
        }
    };

    private BroadcastReceiver ContactChangedRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 刷新
            refreshContact();
        }
    };


    @Override
    protected void initView() {
        super.initView();

        titleBar.setRightImageResource(R.drawable.em_add);

        View headerView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);

        iv_contactlist_red = (ImageView) headerView.findViewById(R.id.iv_contactlist_red);
        iv_contactlist_red.setVisibility(View.GONE);

        ll_contact_invite = (LinearLayout) headerView.findViewById(R.id.ll_contact_invite);

        listView.addHeaderView(headerView);

        // 设置联系人列表的每条点击事件
        setContactListItemClickListener(new EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {
                if (user == null) {
                    return;
                }
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, user.getUsername());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void setUpView() {
        super.setUpView();

        // 添加按钮的点击事件处理
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });

        // 好友邀请的点击事件
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏红点
                iv_contactlist_red.setVisibility(View.GONE);
                SpUtils.getInstace().save(SpUtils.IS_NEW_INVITE, false);
                // 跳转
                startActivity(new Intent(getActivity(), InviteActivity.class));
            }
        });

        // 获取红点状态并显示
        boolean isNewInvite = SpUtils.getInstace().getBoolean(SpUtils.IS_NEW_INVITE, false);
        iv_contactlist_red.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);

        // 广播的监听
        mBroadcastManager = LocalBroadcastManager.getInstance(RollChatApplication.getAppContext());
        // 邀请改变广播
        mBroadcastManager.registerReceiver(InviteChangedRecevier, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        mBroadcastManager.registerReceiver(ContactChangedRecevier, new IntentFilter(Constant.CONTACT_CHANGED));

        // 从环信服务器获取所有的联系人消息
        getContactFromServer();

        //绑定listview和contextmenu
        registerForContextMenu(listView);

    }

    /**
     * 添加弹出菜单的布局
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        EaseUser easeUser = (EaseUser) listView.getItemAtPosition((((AdapterView.AdapterContextMenuInfo) menuInfo).position));

        mHxid = easeUser.getUsername();

        // 添加布局
        getActivity().getMenuInflater().inflate(R.menu.delete, menu);

    }

    /**
     * 弹出菜单的单击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.contact_delete) {
            // 删除选择的联系人
            deleteContact();

            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void deleteContact() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 环信服务上删除
                    EMClient.getInstance().contactManager().deleteContact(mHxid);
                    // 本地数据库删除
                    Model.getInstance().getInCoManager().getContactDAO().deleteContactByHxid(mHxid);
                    // 刷新页面
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(getActivity(), "删除成功~");
                            refreshContact();
                        }
                    });

                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    if (getActivity() == null) {
                        return;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(getActivity(), "删除失败：" + e.getMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * 得到所有的联系人
     */
    private void getContactFromServer() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 获取到所有联系人的hxid
                    List<String> hxids = EMClient.getInstance().contactManager().getAllContactsFromServer();

                    List<UserInfo> contacts = new ArrayList<UserInfo>();

                    if (hxids != null && hxids.size() >= 0) {
                        // 转换
                        for (String hxid : hxids) {
                            UserInfo user = new UserInfo(hxid);
                            contacts.add(user);
                        }

                        // 保存联系人信息到数据库
                        Model.getInstance().getInCoManager().getContactDAO().saveContacts(contacts, true);

                        if (getActivity() == null) {
                            return;
                        }
                        // 刷新页面
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 刷新联系人页面
                                refreshContact();
                            }
                        });

                    }

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshContact() {
        // 获取环信服务器上联系人的数据
        List<ContactInfo> contacts = Model.getInstance().getInCoManager().getContactDAO().getContacts();

        if (contacts != null && contacts.size() >= 0) {
            // 转换数据
            Map<String, EaseUser> contactsMap = new HashMap<>();

            for (ContactInfo contactInfo : contacts) {
                EaseUser easeUser = new EaseUser(contactInfo.getHxid());
                contactsMap.put(contactInfo.getHxid(), easeUser);
            }
            // 设置联系人列表
            setContactsMap(contactsMap);
            // 刷新页面
            refresh();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mBroadcastManager.unregisterReceiver(InviteChangedRecevier);
        mBroadcastManager.unregisterReceiver(ContactChangedRecevier);
    }


}













