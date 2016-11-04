package com.wrk.rollchat.controller.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wrk.rollchat.R;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.utils.EditTextShakeHelper;
import com.wrk.rollchat.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivty extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv)
    CardView cv;
    @BindView(R.id.fab)
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);
        initWindow();
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        final String edt_Name = etUsername.getText().toString().trim();
        final String edt_pwd = etPassword.getText().toString().trim();

        switch (view.getId()) {
            case R.id.fab:
                enterRegistActivity();
                break;
            case R.id.bt_go:
                // 获取输入的用户名和密码并校验
                if (TextUtils.isEmpty(edt_Name) || TextUtils.isEmpty(edt_pwd)) {
                    ToastUtil.showToast(LoginActivty.this, "用户名或密码不能为空");
                    new EditTextShakeHelper(LoginActivty.this).shake(etPassword, etUsername);
                    return;
                }
                // 登录环信服务器
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().login(edt_Name, edt_pwd, new EMCallBack() {
                            @Override
                            public void onSuccess() {
                                pbLoading.setVisibility(View.GONE);
                                // 模型层数据的初始化
                                Model.getInstance().loginSuccess(new UserInfo(edt_Name));
                                // 保存登录信息
                                Model.getInstance().getAccountDAO().addAccount(new UserInfo(edt_Name));

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // 跳转到主页面
                                        enterMainActivity();
                                    }
                                });
                            }

                            @Override
                            public void onError(int i, final String s) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showToast(LoginActivty.this, s);
                                        pbLoading.setVisibility(View.GONE);
                                        btGo.setVisibility(View.VISIBLE);
                                    }
                                });
                            }

                            @Override
                            public void onProgress(int i, String s) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btGo.setVisibility(View.GONE);
                                        pbLoading.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });
                    }
                });

                break;
        }
    }

    private void enterRegistActivity() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(null);
            getWindow().setEnterTransition(null);
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivityForResult(new Intent(this, RegistActivity.class), 1, options.toBundle());
        } else {
            startActivityForResult(new Intent(this, RegistActivity.class), 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            Log.e("111", data.getStringExtra("username"));
            etUsername.setText(data.getStringExtra("username"));
        }
    }

    public void enterMainActivity() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(500);

            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            startActivity(new Intent(this, MainActivity.class), options.toBundle());
            finish();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏颜色
            int color = getResources().getColor(R.color.statusBar);
            getWindow().setStatusBarColor(color);
        }
    }

}
