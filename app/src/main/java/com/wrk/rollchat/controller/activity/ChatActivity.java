package com.wrk.rollchat.controller.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wrk.rollchat.R;

public class ChatActivity extends FragmentActivity {

    private String mHxid;
    private EaseChatFragment mEaseChatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initData();

        initListener();

    }

    private void initData() {
        // 创建一个会话的Fragment
        mEaseChatFragment = new EaseChatFragment();

        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);

        mEaseChatFragment.setArguments(getIntent().getExtras());

        // 替换Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_chat, mEaseChatFragment)
                .commit();
    }

    private void initListener() {
    }
}
