package com.example.androidmvvmtest.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.databinding.ActivityHomeBinding;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.MVUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/4 21:36
 * @Description: 包容显示Fragment的主要的Activity
 */
public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding mBinding;
    private int lastCheck = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.ImgTheme);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();//初始化视图
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //获取navController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        disableNavViewLongClick(mBinding.bnav);
        mBinding.bnav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.biying_pic_fragment) {
                    if (lastCheck==0){
                        return false;
                    }
                    navController.navigate(R.id.biying_pic_fragment);
                    lastCheck = 0;
                } else if (id == R.id.info_fragment) {
                    if (lastCheck==1){
                        return false;
                    }
                    navController.navigate(R.id.info_fragment);
                    lastCheck = 1;
                } else if (id == R.id.daily_fragment) {
                    if (lastCheck==2){
                        return false;
                    }
                    navController.navigate(R.id.daily_fragment);
                    lastCheck = 2;
                }
                return true;
            }
        });

        //nav
        mBinding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.item_logout) {
                    logout();
                }
                return true;
            }
        });
    }

    /**
     * BottomNavigationView禁用Toast提示
     *
     * @param bottomNavigationView 控件
     */
    private void disableNavViewLongClick(BottomNavigationView bottomNavigationView) {
        assert bottomNavigationView != null;
        final int childCount = bottomNavigationView.getChildCount();
        if (childCount < 0) {
            return;
        }
        final View childAtView = bottomNavigationView.getChildAt(0);
        if (!(childAtView instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) childAtView;
        int viewGroupChildCount = viewGroup.getChildCount();
        for (int i = 0; i < viewGroupChildCount; i++) {
            View v = viewGroup.getChildAt(i);
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        showSortMsg("退出登录");
        MVUtils.put(Constant.IS_LOGIN, false);
        jumpActivityFinish(LoginActivity.class);
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
