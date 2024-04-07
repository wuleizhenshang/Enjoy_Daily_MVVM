package com.example.androidmvvmtest.ui.activity;

import android.os.Build;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/4 21:36
 * @Description: 包容显示Fragment的主要的Activity
 */
public class HomeActivity extends BaseActivity {

    private ActivityHomeBinding mBinding;

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
                    navController.navigate(R.id.biying_pic_fragment);
                } else if (id == R.id.news_fragment) {
                    navController.navigate(R.id.news_fragment);
                } else if (id == R.id.video_fragment) {
                    navController.navigate(R.id.video_fragment);
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
}
