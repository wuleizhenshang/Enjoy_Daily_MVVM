package com.example.androidmvvmtest.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.FragmentNewsBinding;
import com.example.androidmvvmtest.viewmodels.NewsViewModel;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/4/3
 * @Discribe: 展示新闻列表的fragment
 */
public class NewsFragment extends Fragment {

    private NewsViewModel mViewModel;
    private FragmentNewsBinding newsBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        newsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false);
        return newsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
    }


}