package com.example.androidmvvmtest.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.FragmentVideoBinding;
import com.example.androidmvvmtest.databinding.ItemVideoShowPicBinding;
import com.example.androidmvvmtest.network.bean.response.VideoResponseBean;
import com.example.androidmvvmtest.ui.adapter.base.BaseDataBindingViewHolder;

import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/6 16:22
 * @Description: 视频展示页面的rec都适配器
 */
public class VideoAdapter extends BaseQuickAdapter<VideoResponseBean.ResultDTO, BaseDataBindingViewHolder<ItemVideoShowPicBinding>> {

    public VideoAdapter(@NonNull List<? extends VideoResponseBean.ResultDTO> items) {
        super(items);
    }

    @Override
    protected void onBindViewHolder(@NonNull BaseDataBindingViewHolder<ItemVideoShowPicBinding> itemVideoShowPicBindingBaseDataBindingViewHolder, int i, @Nullable VideoResponseBean.ResultDTO resultDTO) {
        ItemVideoShowPicBinding binding = itemVideoShowPicBindingBaseDataBindingViewHolder.getDataBinding();
        binding.setVideoBean(resultDTO);
        binding.executePendingBindings();
    }

    @NonNull
    @Override
    protected BaseDataBindingViewHolder<ItemVideoShowPicBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return BaseDataBindingViewHolder.create(viewGroup, R.layout.item_video_show_pic);
    }
}
