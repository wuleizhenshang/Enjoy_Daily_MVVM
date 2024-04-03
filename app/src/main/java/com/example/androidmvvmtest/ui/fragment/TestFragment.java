package com.example.androidmvvmtest.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidmvvmtest.ui.adapter.NewsAdapter;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.FragmentTestBinding;
import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.viewmodels.NewsViewModel;

import java.util.List;

public class TestFragment extends Fragment {

    FragmentTestBinding mFragmentTestBinding;
    private NewsViewModel mNewsViewModel;
    private String mSelect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false);

        return mFragmentTestBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mSelect = bundle.getString("string");
        }


        //viewmodel
        mNewsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        mNewsViewModel.getNewsBean(mSelect, 20, 0).observe(getViewLifecycleOwner(), new Observer<List<NewsResponseBean.NewsBean>>() {
            @Override
            public void onChanged(List<NewsResponseBean.NewsBean> newsBeans) {
                mFragmentTestBinding.rec.setAdapter(new NewsAdapter(newsBeans));
            }
        });


        mFragmentTestBinding.rec.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}