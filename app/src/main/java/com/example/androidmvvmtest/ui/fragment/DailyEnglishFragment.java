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
import com.example.androidmvvmtest.databinding.FragmentDailyBinding;
import com.example.androidmvvmtest.databinding.FragmentDailyEnglishBinding;
import com.example.androidmvvmtest.db.room.entity.DailyEnglish;
import com.example.androidmvvmtest.viewmodels.DailyViewModel;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 22:10
 * @Description: TODO
 */
public class DailyEnglishFragment extends BaseFragment {
    FragmentDailyEnglishBinding mBinding;
    DailyViewModel mDailyViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_english, container, false);
        mDailyViewModel = new ViewModelProvider(this).get(DailyViewModel.class);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDailyEnglish();
    }

    private void initDailyEnglish() {
        mDailyViewModel.getDailyEnglish().observe(context, new Observer<DailyEnglish>() {
            @Override
            public void onChanged(DailyEnglish dailyEnglish) {
                mBinding.setDailyEnglish(dailyEnglish);

            }
        });

        mDailyViewModel.getDailyEnglishError().observe(context, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showCustomMsg(s, 1500);
            }
        });
    }
}
