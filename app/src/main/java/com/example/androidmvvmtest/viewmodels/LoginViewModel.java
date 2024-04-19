package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.bean.User;
import com.example.androidmvvmtest.network.bean.request.WanAndroidLoginRequestBean;
import com.example.androidmvvmtest.network.bean.request.WanAndroidRegisterRequestBean;
import com.example.androidmvvmtest.repository.LoginRepository;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class LoginViewModel extends ViewModel {
    private final LoginRepository mLoginRepository = new LoginRepository();//仓库
    private MutableLiveData<User> user;//用户名和密码的输入和保存，与View双向绑定
    private final MutableLiveData<Boolean> isLoginSuccess = new MutableLiveData<>();//是否登录成功
    private final MutableLiveData<String> loginMsg = new MutableLiveData<>();//登录api返回信息
    private final MutableLiveData<Integer> loginCount = new MutableLiveData<>();//统计点击登录的次数
    private final MutableLiveData<Boolean> isRegister = new MutableLiveData<>();//是否注册
    private final MutableLiveData<String> registerMsg = new MutableLiveData<>();//注册api返回信息

    /**
     * 获取user
     *
     * @return user
     */
    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>();
        }
        return user;
    }

    /**
     * 登录
     *
     * @param wanAndroidLoginRequestBean
     */
    public void login(WanAndroidLoginRequestBean wanAndroidLoginRequestBean) {
        if (loginCount.getValue() == null) {
            loginCount.setValue(1);
        } else {
            loginCount.setValue(loginCount.getValue() + 1);
        }
        mLoginRepository.login(wanAndroidLoginRequestBean);
    }

    /**
     * 获取是否登录
     *
     * @return isLogin
     */
    public MutableLiveData<Boolean> getIsLoginSuccess() {
        return mLoginRepository.getIsLoginSuccess();
    }

    /**
     * 获取登录信息
     *
     * @return loginMsg
     */
    public MutableLiveData<String> getLoginMsg() {
        return mLoginRepository.getLoginMsg();
    }

    /**
     * 获取点击登录按钮的次数
     *
     * @return count
     */
    public MutableLiveData<Integer> getLoginCount() {
        return loginCount;
    }

    /**
     * 注册
     *
     * @param wanAndroidRegisterRequestBean data
     */
    public void register(WanAndroidRegisterRequestBean wanAndroidRegisterRequestBean) {
        mLoginRepository.register(wanAndroidRegisterRequestBean);
    }

    /**
     * 获取注册api返回信息
     *
     * @return registerMsg
     */
    public MutableLiveData<String> getRegisterMsg() {
        return mLoginRepository.getRegisterMsg();
    }

    /**
     * 获取是否注册
     *
     * @return isRegister
     */
    public MutableLiveData<Boolean> getIsRegister() {
        return mLoginRepository.getIsRegister();
    }
}
