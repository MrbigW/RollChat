package com.wrk.rollchat.controller.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.wrk.rollchat.R;
import com.wrk.rollchat.controller.fragment.ChatFragment;
import com.wrk.rollchat.controller.fragment.ContactListFragment;
import com.wrk.rollchat.controller.fragment.SettingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    private ChatFragment mChatFragment;
    private ContactListFragment mContactListFragment;
    private SettingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initWindow();

        initData();

        initListner();
    }

    private void initListner() {
        // RadioGruop的选择事件
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Fragment fragment = null;

                switch (checkedId) {
                    // 会话页面
                    case R.id.rb_main_chat:
                        fragment = mChatFragment;
                        break;
                    // 联系人页面
                    case R.id.rb_main_contact:
                        fragment = mContactListFragment;
                        break;
                    // 设置界面
                    case R.id.rb_main_setting:
                        fragment = mSettingFragment;
                        break;
                }

                switchFragment(fragment);

            }
        });
        // 默认选择会话列表页面
        rgMain.check(R.id.rb_main_chat);
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main, fragment).commit();
    }

    private void initData() {
        // 创建fragment对象
        mChatFragment = new ChatFragment();
        mContactListFragment = new ContactListFragment();
        mSettingFragment = new SettingFragment();

    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏颜色
            int color = getResources().getColor(R.color.statusBar);
            getWindow().setStatusBarColor(color);
        }
    }

}
