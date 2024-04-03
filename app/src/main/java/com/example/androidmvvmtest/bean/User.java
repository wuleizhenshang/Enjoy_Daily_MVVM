package com.example.androidmvvmtest.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.androidmvvmtest.BR;


/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class User extends BaseObservable {

    public String account;
    public String pwd;

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }

    public User(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }
}

