package com.example.androidmvvmtest.network.Interface;

import com.example.androidmvvmtest.network.bean.response.VideoResponseBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/6 14:58
 * @Description: 聚合数据获取视频列表的接口，简单获取一个列表而已
 */
public interface IVideo {

    public static final String KEY = "75e4e52e182947744e817b1de19bb331";

    public static final String TYPE = "hot_video";//固定值

    //这里不分类型，只获取热门的先,size为10-50
    @GET("/fapig/douyin/billboard")
    Observable<VideoResponseBean> getVideoList(@Query("key") String key,@Query("type") String type,
                                               @Query("size") int size);
}
