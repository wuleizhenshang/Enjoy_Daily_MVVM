package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.network.bean.response.BiYingResponse;
import com.example.androidmvvmtest.network.bean.response.WallPaperResponse;
import com.example.androidmvvmtest.repository.BiYingRepository;
import com.example.androidmvvmtest.repository.BiYingShowPicRepository;

import dagger.hilt.android.scopes.ViewModelScoped;

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

    private final BiYingRepository mRepository = new BiYingRepository();


    public void getBiYing() {
        mBiYingResponseLiveData = mRepository.getBiYingData();
    }

    /**
     * 热门壁纸
     */
    public LiveData<WallPaperResponse> wallPaper;

    public void getWallPaper() { wallPaper = mRepository.getWallPaper(); }

}
