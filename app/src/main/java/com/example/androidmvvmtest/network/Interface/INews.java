package com.example.androidmvvmtest.network.Interface;

import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.network.bean.response.NewsTitleResponseBean;
import com.example.androidmvvmtest.network.bean.response.Result;
import com.example.androidmvvmtest.network.bean.response.TTBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public interface INews {

    public static final String KEY = "83e56ea467f22965e4579c11b44ced32";//key

    @GET("/api/toutiao/v1/channel")
    Observable<Result<NewsTitleResponseBean>> getNewsTitleList(@Query("key") String key);

    //num:10-40,默认10 start:表示起始位置，10-40，默认0，相当于offset
    @GET("/api/toutiao/v1/index")
    Observable<Result<NewsResponseBean>> getNewsList(@Query("key") String key, @Query("type") String type, @Query("num") int num, @Query("start") int start);


    @GET("shadiao")
    Observable<TTBean> getShaDiao();
}
