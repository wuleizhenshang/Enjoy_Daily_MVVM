package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.bean.User;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class LoginViewModel extends ViewModel {
    public MutableLiveData<User> user;

    public MutableLiveData<User> getUser() {
        if (user==null){
            user = new MutableLiveData<>();
        }
        return user;
    }
}
