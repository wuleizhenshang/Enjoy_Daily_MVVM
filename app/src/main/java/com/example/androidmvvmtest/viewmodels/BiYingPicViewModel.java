package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.network.bean.response.BiYingResponse;
import com.example.androidmvvmtest.network.bean.response.WallPaperResponse;
import com.example.androidmvvmtest.repository.BiYingRepository;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class BiYingPicViewModel extends ViewModel {
    /**
     * 必应每日一图
     */
    public LiveData<BiYingResponse> mBiYingResponseLiveData;

    public void getBiYing() {
        mBiYingResponseLiveData = new BiYingRepository().getBiYingData();
    }

    /**
     * 热门壁纸
     */
    public LiveData<WallPaperResponse> wallPaper;

    public void getWallPaper() { wallPaper = new BiYingRepository().getWallPaper(); }

}
