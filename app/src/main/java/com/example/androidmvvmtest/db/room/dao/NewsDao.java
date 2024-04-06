package com.example.androidmvvmtest.db.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidmvvmtest.db.room.entity.News;
import com.example.androidmvvmtest.db.room.entity.NewsChannel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/5 13:31
 * @Description: 操作新闻数据表的操作类
 */
@Dao
public interface NewsDao {
    @Query("SELECT * From news")
    Flowable<List<News>> getAllNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertNews(List<News> newsList);

    @Query("DELETE FROM news")
    Completable deleteAllNews();

    //根据名字获取的第几到第几条数据
    @Query("SELECT * FROM news WHERE channel=:channel LIMIT:limit OFFSET :start")
    Flowable<List<News>> getOffsetNewsByChannel(String channel, int start, int limit);//limit获取多少条，start为开始的位置
}
