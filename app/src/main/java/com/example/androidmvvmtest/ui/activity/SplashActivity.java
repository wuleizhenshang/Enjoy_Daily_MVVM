package com.example.androidmvvmtest.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
//        setStatusBar(true);//设置状态栏为深色
        //根据是否登录进行跳转，这里就是模拟一下
        EasyAnimation.moveViewWidth(binding.tvTranslate, new EasyAnimation.TranslateCallback() {
            @Override
            public void animationEnd() {
                binding.tvMvvm.setVisibility(View.VISIBLE);
                jumpActivityFinish(MVUtils.getBoolean(Constant.IS_LOGIN) ? HomeActivity.class : LoginActivity.class);
            }
        });
    }

    private long timeMillis;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - timeMillis) > 2000) {
                showCustomMsg(context.getString(R.string.press_twice_to_exit),1500);
                timeMillis = System.currentTimeMillis();
            } else {
                exitTheProgram();
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}