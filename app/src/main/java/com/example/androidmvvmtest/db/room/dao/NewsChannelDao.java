package com.example.androidmvvmtest.db.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidmvvmtest.db.room.entity.NewsChannel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/5 13:31
 * @Description: 操作news_channel表
 */
@Dao
public interface NewsChannelDao {
    @Query("SELECT * From news_channel")
    Flowable<List<NewsChannel>>getAllNewsChannel();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNewsChannel(List<NewsChannel> newsChannels);

    @Query("DELETE FROM news_channel")
    Completable deleteAllNewsChannel();
}
