package com.example.androidmvvmtest.network.Interface;

import com.example.androidmvvmtest.network.bean.response.DailyContentResponseBean;
import com.example.androidmvvmtest.network.bean.response.DailyEnglishResponseBean;

import io.reactivex.Observable;
import okhttp3.Response;
import retrofit2.http.GET;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 20:02
 * @Description: 每日一言和每日英语的retrofit接口
 */
public interface IDaily {

    public static final String KEY = "87a623142c4350a2e16fda66743c61b4";

    @GET("/one/index?key=87a623142c4350a2e16fda66743c61b4")
    Observable<DailyContentResponseBean> getDailyContent();

    @GET("/everyday/index?key=87a623142c4350a2e16fda66743c61b4")
    Observable<DailyEnglishResponseBean> getDailyEnglish();
}
