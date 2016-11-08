package com.wrk.rollchat.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wrk.rollchat.R;
import com.wrk.rollchat.controller.activity.LoginActivty;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by MrbigW on 2016/11/4.
 * weChat:1024057635
 * GitHub:MrbigW
 * Usage: 设置页面
 * -------------------=.=------------------------
 */

public class SettingFragment extends Fragment {

    @BindView(R.id.btn_setting_out)
    Button btnSettingOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        // 在button上显示当前用户的名称
        btnSettingOut.setText("退出登录（" + EMClient.getInstance().getCurrentUser() + "）");
        btnSettingOut.setAllCaps(false);
    }



    @OnClick(R.id.btn_setting_out)
    public void onClick() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 登录环信服务器退出登录
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // 关闭DBhelper
                        Model.getInstance().getInCoManager().close();

                        // 回到登录页面
                        // 更新UI显示
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getActivity(), "退出成功");
                                startActivity(new Intent(getActivity(), LoginActivty.class));
                                getActivity().finish();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, final String s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(getActivity(), "退出失败：" + s);
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
}
