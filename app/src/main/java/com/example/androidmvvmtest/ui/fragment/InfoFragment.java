package com.example.androidmvvmtest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseFragment;
import com.example.androidmvvmtest.databinding.FragmentInfoBinding;
import com.example.androidmvvmtest.ui.adapter.InfoFragmentVP2Adapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 12:02
 * @Description: TODO
 */
public class InfoFragment extends BaseFragment {
    private FragmentInfoBinding mBinding;
    private String[] tabName;
    private List<Fragment>mFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTabAndVP2();//初始化TabLayout和ViewPager2
    }


    /**
     * 初始化TabLayout和ViewPager2
     */
    private void initTabAndVP2(){
        tabName = new String[]{getString(R.string.news_title),getString(R.string.video_title)};
        mFragments.add(new NewsFragment());
        mFragments.add(new VideoFragment());
        mBinding.vp2.setAdapter(new InfoFragmentVP2Adapter(context,tabName,mFragments));
        //将tabLayout和ViewPager2进行绑定
        new TabLayoutMediator(mBinding.tabLayout, mBinding.vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabName[position]);
                mBinding.vp2.setCurrentItem(position);
            }
        }).attach();//记得attach()
        mBinding.vp2.setCurrentItem(0);
    }
}
