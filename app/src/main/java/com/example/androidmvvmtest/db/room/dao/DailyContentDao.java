package com.example.androidmvvmtest.db.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidmvvmtest.db.room.entity.DailyContent;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/17 9:05
 * @Description: TODO
 */
@Dao
public interface DailyContentDao {
    //获取今日的数据
    @Query("SELECT * FROM dailycontent ORDER BY id DESC LIMIT 1")
    Flowable<DailyContent> getDailyContent();

    //获取开始位置和指定数量的内容
    @Query("SELECT * FROM dailycontent LIMIT:limit OFFSET :start")
    Flowable<List<DailyContent>> getOffsetDailyContent(int start, int limit);//limit获取多少条，start为开始的位置

    //插入数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(DailyContent dailyContent);//Completable就是操作完成的回调，可以感知操作成功或失败， onComplete和onError。

    //删除全部
    @Query("DELETE FROM dailycontent")
    Completable deleteAll();

    //TODO 后期可以考虑从应用安装开始就一直每天获取最新的插入到数据库，反向读，获取的时候每次读取指定数量，后续刷新加载更多
}
