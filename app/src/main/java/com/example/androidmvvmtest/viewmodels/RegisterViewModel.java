package com.example.androidmvvmtest.viewmodels;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.network.bean.request.WanAndroidRegisterRequestBean;
import com.example.androidmvvmtest.repository.RegisterRepository;


/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/5/22 8:44
 * @Description: 注册页面的ViewModel
 */
public class RegisterViewModel extends ViewModel {
    private RegisterRepository mRegisterRepository;

    public RegisterViewModel() {
        mRegisterRepository = new RegisterRepository();
    }

    /**
     * 监听返回信息
     *
     * @return msg
     */
    public MutableLiveData<String> getMsg() {
        return mRegisterRepository.getMsg();
    }

    /**
     * 双向绑定，监听注册bean
     *
     * @return bean
     */
    public MutableLiveData<WanAndroidRegisterRequestBean> getRequestBeanMutableLiveData() {
        return mRegisterRepository.getRequestBeanMutableLiveData();
    }

    /**
     * 注册
     */
    @SuppressLint("CheckResult")
    public void register() {
        mRegisterRepository.register();
    }
}
