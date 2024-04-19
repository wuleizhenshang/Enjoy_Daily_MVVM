package com.example.androidmvvmtest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseFragment;
import com.example.androidmvvmtest.databinding.FragmentDailyBinding;
import com.example.androidmvvmtest.db.room.entity.DailyEnglish;
import com.example.androidmvvmtest.ui.adapter.InfoFragmentVP2Adapter;
import com.example.androidmvvmtest.viewmodels.DailyViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 21:18
 * @Description: TODO
 */
public class DailyFragment extends BaseFragment {

    private FragmentDailyBinding mBinding;
    private DailyViewModel mDailyViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily, container, false);
        mDailyViewModel = new ViewModelProvider(this).get(DailyViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVP2();//设置VP2
    }

    /**
     * 设置vp2
     */
    private void initVP2(){
        mDailyViewModel.getFragments().observe(context, new Observer<List<Fragment>>() {
            @Override
            public void onChanged(List<Fragment> fragments) {
                mBinding.vp2.setAdapter(new InfoFragmentVP2Adapter(context,null,fragments));
                mBinding.vp2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                mBinding.vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        mDailyViewModel.getTabs().observe(context, new Observer<String[]>() {
                            @Override
                            public void onChanged(String[] strings) {
                                mBinding.toolbar.setTitle(strings[position]);
                            }
                        });
                    }
                });
            }
        });
    }
}
