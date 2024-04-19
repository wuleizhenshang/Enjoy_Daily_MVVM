package com.example.androidmvvmtest.network.Interface;

import com.example.androidmvvmtest.network.bean.request.WanAndroidLoginRequestBean;
import com.example.androidmvvmtest.network.bean.request.WanAndroidRegisterRequestBean;
import com.example.androidmvvmtest.network.bean.response.WanAndroidLoginResponseBean;
import com.example.androidmvvmtest.network.bean.response.WanAndroidRegisterResponseBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/19 10:44
 * @Description: TODO
 */
public interface IWanAndroid {
    @POST("/user/login")
    @FormUrlEncoded
    Observable<WanAndroidLoginResponseBean>login(@Field("username") String username, @Field("password") String password);


    @POST("/user/register")
    @FormUrlEncoded
    Observable<WanAndroidRegisterResponseBean>register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);
}
