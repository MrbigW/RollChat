package com.wrk.rollchat.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wrk.rollchat.R;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.model.bean.UserInfo;
import com.wrk.rollchat.utils.EditTextShakeHelper;
import com.wrk.rollchat.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContactActivity extends AppCompatActivity {

    @BindView(R.id.tv_add_find)
    TextView tvAddFind;
    @BindView(R.id.et_add_name)
    EditText etAddName;
    @BindView(R.id.tv_add_name)
    TextView tvAddName;
    @BindView(R.id.btn_add_add)
    Button btnAddAdd;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;

    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_add_find, R.id.btn_add_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_find:
                find();
                break;
            case R.id.btn_add_add:
                add();
                break;
        }
    }

    private void find() {
        // 获取输入的用户名称
        final String name = etAddName.getText().toString().trim();
        // 校验输入的名称
        if (TextUtils.isEmpty(name)) {
            new EditTextShakeHelper(AddContactActivity.this).shake(etAddName);
            ToastUtil.showToast(AddContactActivity.this, "输入的用户名称不能为空");
            return;
        }

        // 去服务器判断当前用户是否存在
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                mUserInfo = new UserInfo(name);
                // 更新UI显示
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rlAdd.setVisibility(View.VISIBLE);
                        tvAddName.setText(mUserInfo.getName());
                    }
                });
            }
        });

    }

    // 添加用户
    private void add() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(mUserInfo.getName(), "添加好友");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "发送添加好友邀请失败" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        });
    }
}










