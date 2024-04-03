package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.db.room.entity.WallPaper;
import com.example.androidmvvmtest.repository.BiYingShowPicRepository;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe:
 */
public class BiYingShowPicViewModel extends ViewModel {
    public LiveData<List<WallPaper>> wallPapers;

    public void getWallPapers() {
        wallPapers = new BiYingShowPicRepository().getWallPapers();
    }
}
