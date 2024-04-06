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
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.base.BaseFragment;
import com.example.androidmvvmtest.databinding.FragmentBiyingPicBinding;
import com.example.androidmvvmtest.network.bean.response.BiYingResponse;
import com.example.androidmvvmtest.network.bean.response.WallPaperResponse;
import com.example.androidmvvmtest.ui.adapter.WallPaperAdapter;
import com.example.androidmvvmtest.viewmodels.BiYingPicViewModel;
import com.google.android.material.appbar.AppBarLayout;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/4 21:28
 * @Description: 展示图片的fragment
 */
public class BiYingPicFragment extends BaseFragment {

    private FragmentBiyingPicBinding mBinding;

    private BiYingPicViewModel mBiYingPicViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_biying_pic, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBiYingPicViewModel = new ViewModelProvider(this).get(BiYingPicViewModel.class);
        initAppBar();//设置顶部变化
        initBiYingPerPic();//获取每日一图
        initHotPics();//获取热门壁纸
    }

    /**
     * 获取每日一图
     */
    private void initBiYingPerPic() {
        //获取必应每日一图
        mBiYingPicViewModel.getBiYing();//网络请求获取图片
        //返回数据时更新ViewModel，ViewModel更新则xml更新
        mBiYingPicViewModel.mBiYingResponseLiveData.observe(context, new Observer<BiYingResponse>() {
            @Override
            public void onChanged(BiYingResponse biYingResponse) {
                mBinding.setBingViewmodel(mBiYingPicViewModel);
            }
        });
    }

    /**
     * 获取热门图片
     */
    private void initHotPics() {
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        mBinding.rec.setLayoutManager(manager);
        //获取数据
        mBiYingPicViewModel.getWallPaper();
        mBiYingPicViewModel.wallPaper.observe(context, new Observer<WallPaperResponse>() {
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
