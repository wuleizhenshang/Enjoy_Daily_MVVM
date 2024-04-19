package com.example.androidmvvmtest.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 12:18
 * @Description: 热门资讯页面的ViewPager2的适配器
 */
public class InfoFragmentVP2Adapter extends FragmentStateAdapter {

    String[] tabName;
    List<Fragment> mFragments;

    public InfoFragmentVP2Adapter(@NonNull FragmentActivity fragmentActivity, String[] tabName, List<Fragment> fragments) {
        super(fragmentActivity);
        this.tabName = tabName;
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments == null ? 0 : mFragments.size();
    }
}
