package com.example.androidmvvmtest.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter4.BaseQuickAdapter;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.ItemBingyingShowPicVpBinding;
import com.example.androidmvvmtest.db.room.entity.WallPaper;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe: 展示大图界面的ViewPager2的适配器
 */


/*通过继承BaseQuickAdapter，通过使用需要适配器中的实体Bean，然后是ViewHolder，这里使用的是BaseDataBindingHolder，
        最终是继承RecyclerView.ViewHolder，同时传递了ItemImageBinding，这是布局在编译时生成的。
        对应了item_image.xml文件。然后在convert方法中，通过bindingHolder.getDataBinding()获取binding，
        然后设置数据，执行executePendingBindings。这里的环节就和普通的RecyclerView.Adapter差不多。*/

public class PicViewVPAdapter extends BaseQuickAdapter<WallPaper, PicViewVPAdapter.ViewHolder> {

    public PicViewVPAdapter(List<WallPaper> data) {
        super(data);
    }

    @Override
    protected void onBindViewHolder(@NonNull PicViewVPAdapter.ViewHolder viewHolder, int i, @Nullable WallPaper wallPaper) {
        ItemBingyingShowPicVpBinding binding = viewHolder.getBinding();
        binding.setWallPaper(wallPaper);
        binding.executePendingBindings();//防止列表闪烁
    }

    @NonNull
    @Override
    protected PicViewVPAdapter.ViewHolder onCreateViewHolder(@NonNull Context context, @NonNull ViewGroup viewGroup, int i) {
        ItemBingyingShowPicVpBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_bingying_show_pic_vp,
                viewGroup, false);
        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemBingyingShowPicVpBinding binding;

        public ViewHolder(ItemBingyingShowPicVpBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemBingyingShowPicVpBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemBingyingShowPicVpBinding binding) {
            this.binding = binding;
        }
    }
}


