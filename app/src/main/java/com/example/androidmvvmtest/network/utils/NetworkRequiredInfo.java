package com.example.androidmvvmtest.network.utils;

import android.app.Application;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe: 网络访问信息实现类
 */
public class NetworkRequiredInfo implements INetworkRequiredInfo {

    private final Application application;

    public NetworkRequiredInfo(Application application) {
        this.application = application;
    }

    /**
     * 版本名
     */
    @Override
    public String getAppVersionName() {
        //return BuildConfig.VERSION_NAME;
        return null;
    }

    /**
     * 版本号
     */
    @Override
    public String getAppVersionCode() {
        //return String.valueOf(BuildConfig.VERSION_CODE);
        return null;
    }

    /**
     * 是否为debug
     */
    @Override
    public boolean isDebug() {
        //return BuildConfig.DEBUG;
        return false;
    }

    /**
     * 应用全局上下文
     */
    @Override
    public Application getApplicationContext() {
        return application;
    }
}
