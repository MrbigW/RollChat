package com.wrk.rollchat.controller.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.hyphenate.chat.EMClient;
import com.wrk.rollchat.R;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.model.bean.UserInfo;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            // 当前Activity已销毁
            if (isFinishing()) {
                return;
            }

            // 跳转到主页面或者登录页面
            toMainOrLogin();
        }

    };

    private void toMainOrLogin() {

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {

                    @Override
                    public void run() {
                        // 判断是否曾经登陆过
                        if (EMClient.getInstance().isLoggedInBefore()) { // 登陆过
                            // 获取登录过的用户信息
                            UserInfo userInfo = Model.getInstance().getAccountDAO().getAccountByHxid(EMClient.getInstance().getCurrentUser());

                            if (userInfo == null) {
                                // 跳转至登录页面
                                startActivity(new Intent(SplashActivity.this, LoginActivty.class));
                            } else {
                                // 登录成功之后
                                Model.getInstance().loginSuccess(userInfo);
                                // 跳转至主页面
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            }
                        } else { // 未登录过
                            startActivity(new Intent(SplashActivity.this, LoginActivty.class));
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            finishAfterTransition();
                        } else {
                            finish();
                        }
                    }
                });

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initWindow();
        // 发送2s延迟消息
        mHandler.sendMessageDelayed(Message.obtain(), 2000);
    }


    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏颜色
            int color = getResources().getColor(R.color.statusBar);
            getWindow().setStatusBarColor(color);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }


}






















