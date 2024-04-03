package com.example.androidmvvmtest.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.ItemNewShowPicBinding;
import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe:
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //数据
    private final List<NewsResponseBean.NewsBean> mNewsBeans;

    public NewsAdapter(List<NewsResponseBean.NewsBean> newsBeans) {
        mNewsBeans = newsBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewShowPicBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_new_show_pic, parent, false);
        return new NewsPicViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemNewShowPicBinding binding = ((NewsPicViewHolder) holder).getBinding();
        binding.setNewsBean(mNewsBeans.get(position));
    }

    @Override
    public int getItemCount() {
        return mNewsBeans == null ? 0 : mNewsBeans.size();
    }

    /**
     * 下面使用dataBinding是不同点
     */
    private class NewsPicViewHolder extends RecyclerView.ViewHolder {

        private ItemNewShowPicBinding mBinding;

        public NewsPicViewHolder(ItemNewShowPicBinding inflate) {
            super(inflate.getRoot());
            this.mBinding = inflate;
        }

        public ItemNewShowPicBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ItemNewShowPicBinding binding) {
            mBinding = binding;
        }

    }
}
