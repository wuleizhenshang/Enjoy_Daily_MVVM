package com.example.androidmvvmtest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.ItemNewShowPicBinding;
import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.ui.activity.WebViewActivity;
import com.example.androidmvvmtest.ui.adapter.base.BaseDataBindingViewHolder;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe:
 */

public class NewsAdapter extends BaseQuickAdapter<NewsResponseBean.NewsBean, BaseDataBindingViewHolder<ItemNewShowPicBinding>>{

    public NewsAdapter(@NonNull List<? extends NewsResponseBean.NewsBean> items) {
        super(items);
    }

    @Override
    protected void onBindViewHolder(@NonNull BaseDataBindingViewHolder<ItemNewShowPicBinding> itemNewShowPicBindingBaseDataBindingViewHolder, int i, @Nullable NewsResponseBean.NewsBean newsBean) {
        ItemNewShowPicBinding dataBinding = itemNewShowPicBindingBaseDataBindingViewHolder.getDataBinding();
        dataBinding.setNewsBean(newsBean);
        dataBinding.setOnClick(new ClickBinding());
        dataBinding.executePendingBindings();
    }

    @NonNull
    @Override
    protected BaseDataBindingViewHolder<ItemNewShowPicBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return BaseDataBindingViewHolder.create(viewGroup,R.layout.item_new_show_pic);
    }

    public static class ClickBinding{
        public void itemClick(NewsResponseBean.NewsBean newsBean, View view){
            Intent intent = new Intent(view.getContext(), WebViewActivity.class);
            intent.putExtra("kind",0);
//           intent.putExtra("url",newsBean.getUrl());
            intent.putExtra("url",newsBean.getContent());
            view.getContext().startActivity(intent);
        }
    }
}