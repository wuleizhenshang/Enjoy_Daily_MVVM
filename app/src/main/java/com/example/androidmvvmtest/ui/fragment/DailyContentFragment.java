package com.example.androidmvvmtest.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseFragment;
import com.example.androidmvvmtest.databinding.FragmentDailyContentBinding;
import com.example.androidmvvmtest.db.room.entity.DailyContent;
import com.example.androidmvvmtest.viewmodels.DailyViewModel;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/17 9:41
 * @Description: TODO
 */
public class DailyContentFragment extends BaseFragment {
    private FragmentDailyContentBinding mBinding;
    private DailyViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_content, container, false);
        mViewModel = new ViewModelProvider(context).get(DailyViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mViewModel.getDailyContent().observe(context, new Observer<DailyContent>() {
            @Override
            public void onChanged(DailyContent dailyContent) {
                mBinding.setDailyContent(dailyContent);
            }
        });

        mViewModel.getDailyContentErrorMSG().observe(context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showCustomMsg(s, 1500);
            }
        });
    }
}
