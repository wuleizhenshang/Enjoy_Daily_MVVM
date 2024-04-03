package com.example.androidmvvmtest.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.base.BaseApplication;
import com.example.androidmvvmtest.db.room.entity.WallPaper;
import com.example.androidmvvmtest.db.room.utils.CustomDisposable;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe:
 */
public class BiYingShowPicRepository {
    public MutableLiveData<List<WallPaper>> wallPaperList = new MutableLiveData<>();

    public MutableLiveData<List<WallPaper>> getWallPapers() {
        Flowable<List<WallPaper>> flowable = BaseApplication.getDb().wallPaperDao().getAll();
        CustomDisposable.addDisposable(flowable, new Consumer<List<WallPaper>>() {
            @Override
            public void accept(List<WallPaper> wallPapers) throws Exception {
                wallPaperList.setValue(wallPapers);
            }
        });
        return wallPaperList;
    }
}
