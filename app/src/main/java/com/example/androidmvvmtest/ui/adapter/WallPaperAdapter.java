package com.example.androidmvvmtest.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.chad.library.adapter4.viewholder.QuickViewHolder;
import com.example.androidmvvmtest.network.bean.response.WallPaperResponse;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.ItemWallPaperBinding;
import com.example.androidmvvmtest.ui.activity.PictureViewActivity;
import com.example.androidmvvmtest.ui.adapter.base.BaseDataBindingViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe: 展示所有热门图片的Adapter
 */
public class WallPaperAdapter extends BaseQuickAdapter<WallPaperResponse.ResBean.VerticalBean, BaseDataBindingViewHolder<ItemWallPaperBinding>> {

    public WallPaperAdapter(@NonNull List<? extends WallPaperResponse.ResBean.VerticalBean> items) {
        super(items);
    }

    @Override
    protected void onBindViewHolder(@NonNull BaseDataBindingViewHolder<ItemWallPaperBinding> itemWallPaperBindingBaseDataBindingViewHolder, int i, @Nullable WallPaperResponse.ResBean.VerticalBean verticalBean) {
        ItemWallPaperBinding binding = itemWallPaperBindingBaseDataBindingViewHolder.getDataBinding();
        binding.setWallpeaper(verticalBean);
        binding.setClick(new ClickBinding());
        binding.executePendingBindings();
    }

    @NonNull
    @Override
    protected BaseDataBindingViewHolder<ItemWallPaperBinding> onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        return BaseDataBindingViewHolder.create(viewGroup,R.layout.item_wall_paper);
    }

    //点击事件
    public static class ClickBinding {
        public void itemClick(WallPaperResponse.ResBean.VerticalBean verticalBean, View view) {
            Intent intent = new Intent(view.getContext(), PictureViewActivity.class);
            intent.putExtra("img", verticalBean.getImg());
            view.getContext().startActivity(intent);
        }
    }
}
