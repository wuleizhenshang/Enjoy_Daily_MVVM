package com.example.androidmvvmtest.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.databinding.ActivitySplashBinding;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.EasyAnimation;
import com.example.androidmvvmtest.utils.MVUtils;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/4/4 0:36
 * @Discribe: 启动页面
 */
public class SplashActivity extends BaseActivity {

    MVUtils mvUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivitySplashBinding.inflate(getLayoutInflater()).getRoot());
        new Handler().postDelayed(() -> jumpActivityFinish(mvUtils.getBoolean(Constant.IS_LOGIN) ? HomeActivity.class : LoginActivity.class),400);
    }
}
