package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.network.Interface.IWanAndroid;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.bean.request.WanAndroidRegisterRequestBean;
import com.example.androidmvvmtest.network.bean.response.WanAndroidRegisterResponseBean;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.MVUtils;
import com.example.androidmvvmtest.utils.ToastConstant;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/5/22 8:45
 * @Description: 注册页面的数据仓库
 */
public class RegisterRepository {
    private MutableLiveData<String> msg = new MutableLiveData<>();//注册信息
    private MutableLiveData<WanAndroidRegisterRequestBean> mRequestBeanMutableLiveData
            = new MutableLiveData<>(
            new WanAndroidRegisterRequestBean("", "", "")
    );//注册网络请求bean

    /**
     * 监听返回信息
     *
     * @return msg
     */
    public MutableLiveData<String> getMsg() {
        return msg;
    }

    /**
     * 双向绑定，监听注册bean
     *
     * @return bean
     */
    public MutableLiveData<WanAndroidRegisterRequestBean> getRequestBeanMutableLiveData() {
        return mRequestBeanMutableLiveData;
    }

    /**
     * 注册
     */
    @SuppressLint("CheckResult")
    public void register() {
        WanAndroidRegisterRequestBean wanAndroidRegisterRequestBean = mRequestBeanMutableLiveData.getValue();
        //异常为空，注册失败
        if (wanAndroidRegisterRequestBean == null) {
            msg.setValue(ToastConstant.REGISTER_FAIL);
            return;
        }
        //正常，进行请求
        NetworkApi.createService(IWanAndroid.class, NetworkApi.WAN_ANDROID).register(wanAndroidRegisterRequestBean.getUsername(), wanAndroidRegisterRequestBean.getRepassword(), wanAndroidRegisterRequestBean.getRepassword()).compose(NetworkApi.applySchedulers(new BaseObserver<WanAndroidRegisterResponseBean>() {
            @Override
            public void onSuccess(WanAndroidRegisterResponseBean s) {
                if (s == null) {
                    msg.setValue(ToastConstant.REGISTER_FAIL);
                    return;
                }
                if (s.getErrorCode() == 0) {
                    msg.setValue(ToastConstant.REGISTER_SUCCESS);
                    //保存到数据库
                    MVUtils.put(Constant.IS_REGISTER, true);
                }else {
                    msg.setValue(s.getErrorMsg());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                msg.setValue(ToastConstant.REGISTER_FAIL);
            }
        }));
    }
}
