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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseFragment;
import com.example.androidmvvmtest.databinding.FragmentVideoBinding;
import com.example.androidmvvmtest.network.bean.response.VideoResponseBean;
import com.example.androidmvvmtest.ui.adapter.VideoAdapter;
import com.example.androidmvvmtest.viewmodels.VideoViewModel;

import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/3 23:44
 * @Description: 展示视频信息列表的fragment
 */
public class VideoFragment extends BaseFragment {

    private VideoViewModel mVideoViewModel;
    private FragmentVideoBinding mVideoBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mVideoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);
        return mVideoBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVideoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);

        showLoading(false);
        mVideoViewModel.getVideoList().observe(context, new Observer<List<VideoResponseBean.ResultDTO>>() {
            @Override
            public void onChanged(List<VideoResponseBean.ResultDTO> resultDTOS) {
                mVideoBinding.rec.setAdapter(new VideoAdapter(resultDTOS));
                mVideoBinding.rec.setLayoutManager(new LinearLayoutManager(context));
                dismissLoading();
            }
        });
    }
}
