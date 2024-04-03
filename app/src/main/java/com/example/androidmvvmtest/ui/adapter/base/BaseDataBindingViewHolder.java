package com.example.androidmvvmtest.ui.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/3 9:44
 * @Description: 带DataBinding的ViewHolder，自动绑定
 */
public class BaseDataBindingViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private final T dataBinding;

    public BaseDataBindingViewHolder(T dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
        // 可以在这里添加事件监听器的绑定逻辑
    }

    public T getDataBinding() {
        return dataBinding;
    }

    // 如果需要，可以添加setDataBinding方法，但通常我们不会在ViewHolder中更改数据绑定对象

    // 提供一个静态方法来加载和绑定布局
    public static <T extends ViewDataBinding> BaseDataBindingViewHolder<T> create(ViewGroup parent, int layoutId) {
        // 加载布局
        T dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutId, parent, false);
        // 创建ViewHolder并返回
        return new BaseDataBindingViewHolder<>(dataBinding);
    }
}

