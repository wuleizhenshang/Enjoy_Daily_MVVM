package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.network.Interface.IWanAndroid;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.bean.request.WanAndroidLoginRequestBean;
import com.example.androidmvvmtest.network.bean.request.WanAndroidRegisterRequestBean;
import com.example.androidmvvmtest.network.bean.response.WanAndroidLoginResponseBean;
import com.example.androidmvvmtest.network.bean.response.WanAndroidRegisterResponseBean;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.MVUtils;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/19 10:54
 * @Description: 登录页面相关数据仓库
 */
public class LoginRepository {
    private final MutableLiveData<Boolean> isRegister = new MutableLiveData<>();//是否注册
    private final MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();//是否登录成功
    private final MutableLiveData<String> loginMsg = new MutableLiveData<>();//登录api返回信息
    private final MutableLiveData<String> registerMsg = new MutableLiveData<>();//注册api返回信息

    /**
     * 登录
     *
     * @param wanAndroidLoginRequestBean 请求数据
     */
    @SuppressLint("CheckResult")
    public void login(WanAndroidLoginRequestBean wanAndroidLoginRequestBean) {
        NetworkApi.createService(IWanAndroid.class, NetworkApi.WAN_ANDROID).login(
                wanAndroidLoginRequestBean.getUsername(),
                wanAndroidLoginRequestBean.getPassword()
        ).compose(NetworkApi.applySchedulers(new BaseObserver<WanAndroidLoginResponseBean>() {
            @Override
            public void onSuccess(WanAndroidLoginResponseBean wanAndroidLoginResponseBean) {
                if (wanAndroidLoginResponseBean == null) {
                    isLoginSuccess.setValue(false);
                    loginMsg.setValue("登录失败！");
                    return;
                }
                if (wanAndroidLoginResponseBean.getErrorCode() == 0) {
                    isLoginSuccess.setValue(true);
                    loginMsg.setValue("登录成功！");
                    //保存到数据库
                    MVUtils.put(Constant.IS_REGISTER, true);
                } else {
                    isLoginSuccess.setValue(false);
                    loginMsg.setValue(wanAndroidLoginResponseBean.getErrorMsg());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                isLoginSuccess.setValue(false);
                loginMsg.setValue("登录失败！" + e.toString());
            }
        }));
    }

    /**
     * 获取登录api返回信息
     *
     * @return loginMsg
     */
    public MutableLiveData<String> getLoginMsg() {
        return loginMsg;
    }

    /**
     * 判断是否登录成功
     *
     * @return isLogin
     */
    public MutableLiveData<Boolean> getIsLoginSuccess() {
        return isLoginSuccess;
    }

    /**
     * 注册
     *
     * @param wanAndroidRegisterRequestBean 请求数据
     */
    @SuppressLint("CheckResult")
    public void register(WanAndroidRegisterRequestBean wanAndroidRegisterRequestBean) {
        NetworkApi.createService(IWanAndroid.class, NetworkApi.WAN_ANDROID).register(wanAndroidRegisterRequestBean.getUsername(), wanAndroidRegisterRequestBean.getRepassword(), wanAndroidRegisterRequestBean.getRepassword()).compose(NetworkApi.applySchedulers(new BaseObserver<WanAndroidRegisterResponseBean>() {
            @Override
            public void onSuccess(WanAndroidRegisterResponseBean s) {
                if (s == null) {
                    isRegister.setValue(false);
                    registerMsg.setValue("注册失败！");
                    return;
                }
                if (s.getErrorCode() == 0) {
                    isRegister.setValue(true);
                    registerMsg.setValue("注册成功，请使用注册的账号密码登录！");
                    //保存到数据库
                    MVUtils.put(Constant.IS_REGISTER, true);
                } else {
                    isRegister.setValue(false);
                    registerMsg.setValue(s.getErrorMsg());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                isRegister.setValue(false);
                registerMsg.setValue("注册失败！");
            }
        }));
    }

    /**
     * 获取注册api返回信息
     *
     * @return registerMsg
     */
    public MutableLiveData<String> getRegisterMsg() {
        return registerMsg;
    }

    /**
     * 获取是否注册
     *
     * @return isRegister
     */
    public MutableLiveData<Boolean> getIsRegister() {
        if (MVUtils.getBoolean(Constant.IS_REGISTER)) {
            isRegister.setValue(true);
        } else {
            isRegister.setValue(false);
        }
        return isRegister;
    }
}
