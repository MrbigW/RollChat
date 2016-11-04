package com.wrk.rollchat.controller.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wrk.rollchat.R;
import com.wrk.rollchat.model.Model;
import com.wrk.rollchat.utils.EditTextShakeHelper;
import com.wrk.rollchat.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends AppCompatActivity {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repeatpassword)
    EditText etRepeatpassword;
    @BindView(R.id.bt_go)
    Button btGo;
    @BindView(R.id.cv_add)
    CardView cvAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        initWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }

    }


    /**
     * 进入的过渡动画
     */
    private void ShowEnterAnimation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
            getWindow().setSharedElementEnterTransition(transition);
            // 过渡动画的监听
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    cvAdd.setVisibility(View.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        transition.removeListener(this);
                        animateRevealShow();
                    }

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }

    private void animateRevealShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // CircularReveal动画
            Animator animator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
            animator.setDuration(500);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    cvAdd.setVisibility(View.VISIBLE);
                    super.onAnimationStart(animation);
                }
            });
            animator.start();
        }
    }

    private void animateRevealClose() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
            mAnimator.setDuration(500);
            mAnimator.setInterpolator(new AccelerateInterpolator());
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    cvAdd.setVisibility(View.INVISIBLE);
                    fab.setImageResource(R.drawable.plus);
                    RegistActivity.super.onBackPressed();
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }
            });
            mAnimator.start();
        }
    }

    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 设置状态栏颜色
            int color = getResources().getColor(R.color.statusBar);
            getWindow().setStatusBarColor(color);
        }
    }

    @OnClick({R.id.fab, R.id.bt_go})
    public void onClick(View view) {
        final String edt_Name = etUsername.getText().toString().trim();
        final String edt_Pwd = etPassword.getText().toString().trim();
        final String edt_RePwd = etRepeatpassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.fab:
                animateRevealClose();
                break;
            case R.id.bt_go:
                // 获取输入的用户名和密码并校验
                if (TextUtils.isEmpty(edt_Name) || TextUtils.isEmpty(edt_Pwd) || TextUtils.isEmpty(edt_RePwd)) {
                    ToastUtil.showToast(RegistActivity.this, "用户名或密码不能为空~");
                    new EditTextShakeHelper(RegistActivity.this).shake(etPassword, etUsername, etRepeatpassword);
                    return;
                }
                if (!edt_Pwd.equals(edt_RePwd)) {
                    ToastUtil.showToast(RegistActivity.this, "两次输入密码不相同~");
                    return;
                }
                // 在环信服务器上注册
                Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().createAccount(edt_Name, edt_Pwd);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(RegistActivity.this, "注册成功");
                                    Intent intent = new Intent();
                                    intent.putExtra("username", edt_Name);
                                    RegistActivity.this.setResult(1, intent);
                                    animateRevealClose();
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                            ToastUtil.showToast(RegistActivity.this, "注册失败:" + e.getMessage());
                        }
                    }
                });
                break;
        }
    }


    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
