package com.wrk.rollchat.controller.fragment;

import android.content.Intent;
import android.view.View;

import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.wrk.rollchat.R;
import com.wrk.rollchat.controller.activity.AddContactActivity;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 联系人列表页面
 * -------------------=.=------------------------
 */

public class ContactListFragment extends EaseContactListFragment {

    @Override
    protected void initView() {
        super.initView();

        titleBar.setRightImageResource(R.drawable.em_add);

        View headerView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);

        listView.addHeaderView(headerView);

    }

    @Override
    protected void setUpView() {
        super.setUpView();

        // 添加按钮的点击事件处理
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddContactActivity.class));
            }
        });

    }

}
