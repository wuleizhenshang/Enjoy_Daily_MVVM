package com.example.androidmvvmtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.utils.KLog;
import com.example.androidmvvmtest.base.BaseApplication;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class CustomImageView extends ShapeableImageView {
    public CustomImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //牛批，真一堆要学的，服气

    /**
     * 必应壁纸  因为拿到的url不完整，因此需要做一次地址拼接
     * @param imageView 图片视图
     * @param url 网络url
     */
    @BindingAdapter(value = {"biyingUrl"}, requireAll = false)
    public static void setBiyingUrl(ImageView imageView, String url) {
        String assembleUrl = "http://cn.bing.com" + url;
        KLog.d(assembleUrl);
        Glide.with(BaseApplication.getContext())
                .load(assembleUrl)
                .placeholder(R.drawable.bg_pic_loading)
                .fallback(R.drawable.bg_pic_loading)
                .error(R.drawable.bg_pic_load_fail)
                .into(imageView);
    }

    /**
     * 普通网络地址图片
     * @param imageView 图片视图
     * @param url 网络url
     */
    @BindingAdapter(value = {"networkUrl"}, requireAll = false)
    public static void setNetworkUrl(ImageView imageView, String url) {
        Glide.with(BaseApplication.getContext())
                .load(url)
                .apply(OPTIONS)//TODO 可以直接通过这种方式设置，这样多个设置相同的代码就可以写到一起
                .into(imageView);
    }


    private static final RequestOptions OPTIONS = new RequestOptions()
            .placeholder(R.drawable.bg_pic_loading)//图片加载出来前，显示的图片
            .fallback(R.drawable.bg_pic_loading) //url为空的时候,显示的图片
            .error(R.drawable.bg_pic_load_fail);//图片加载失败后，显示的图片

}
