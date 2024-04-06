package com.example.androidmvvmtest.db.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidmvvmtest.db.room.entity.News;
import com.example.androidmvvmtest.db.room.entity.Video;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/5 13:31
 * @Description: 操作Video表的Dao类
 */
@Dao
public interface VideoDao {
    @Query("SELECT * From video")
    Flowable<List<Video>> getAllVideo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertVideos(List<Video> videoList);

    @Query("DELETE FROM video")
    Completable deleteAllVideos();
}
