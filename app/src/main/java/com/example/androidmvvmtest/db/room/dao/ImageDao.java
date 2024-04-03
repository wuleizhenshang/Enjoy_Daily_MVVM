package com.example.androidmvvmtest.db.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidmvvmtest.db.room.entity.Image;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe: 使用rxjava2
 */
@Dao
public interface ImageDao {

    @Query("SELECT * FROM image")
    Flowable<Image> getAll();

    @Query("SELECT * FROM image WHERE uid LIKE :uid LIMIT 1")
    Flowable<Image> queryById(int uid);//由于读取速率可能 远大于 观察者处理速率，故使用背压 Flowable 模式

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(Image... images);//Completable就是操作完成的回调，可以感知操作成功或失败， onComplete和onError。

    @Delete
    Completable delete(Image image);
}

