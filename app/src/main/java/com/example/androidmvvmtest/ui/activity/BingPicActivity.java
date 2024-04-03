package com.example.androidmvvmtest.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.androidmvvmtest.ui.adapter.WallPaperAdapter;
import com.example.androidmvvmtest.network.bean.response.BiYingResponse;
import com.example.androidmvvmtest.network.bean.response.WallPaperResponse;
import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.databinding.ActivityBingPicBinding;
import com.example.androidmvvmtest.viewmodels.BingPicViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class BingPicActivity extends AppCompatActivity {

    ActivityBingPicBinding mBinding;

    BingPicViewModel mBingPicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //数据绑定视图
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_bing_pic);
        mBingPicViewModel = new ViewModelProvider(this).get(BingPicViewModel.class);


        initAppBar();//设置顶部变化
        initBiYingPerPic();//获取每日一图
        initHotPics();//获取热门壁纸


    }

    /**
     * 获取每日一图
     */
    private void initBiYingPerPic() {
        //获取必应每日一图
        mBingPicViewModel.getBiYing();//网络请求获取图片
        //返回数据时更新ViewModel，ViewModel更新则xml更新
        mBingPicViewModel.mBiYingResponseLiveData.observe(this, new Observer<BiYingResponse>() {
            @Override
            public void onChanged(BiYingResponse biYingResponse) {
                mBinding.setBingViewmodel(mBingPicViewModel);
            }
        });


    }

    /**
     * 获取热门图片
     */
    private void initHotPics() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mBinding.rec.setLayoutManager(manager);
        //获取数据
        mBingPicViewModel.getWallPaper();
        mBingPicViewModel.wallPaper.observe(this, new Observer<WallPaperResponse>() {
            @Override
            public void onChanged(WallPaperResponse wallPaperResponse) {
                WallPaperAdapter wallPaperAdapter = new WallPaperAdapter(wallPaperResponse.getRes().getVertical());
                mBinding.rec.setAdapter(wallPaperAdapter);
                //mBinding.rec.setAdapter(new WallPaperAdapter(wallPaperResponse.getRes().getVertical()));
            }
        });
    }


    /**
     * 伸缩偏移量监听
     */
    private void initAppBar() {
        mBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {//收缩时
                    mBinding.toolBarLayout.setTitle(getString(R.string.bingying_title));
                    isShow = true;
                } else if (isShow) {//展开时
                    mBinding.toolBarLayout.setTitle("");
                    isShow = false;
                }
            }
        });

    }


}