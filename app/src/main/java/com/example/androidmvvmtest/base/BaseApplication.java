package com.example.androidmvvmtest.base;

import android.app.Application;
import android.content.Context;

import com.example.androidmvvmtest.db.room.database.AppDatabase;
import com.example.androidmvvmtest.utils.MVUtils;
import com.tencent.mmkv.MMKV;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class BaseApplication extends Application {

    public static Context sContext;

    //数据库
    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        sContext = getApplicationContext();

        //MMKV
        MMKV.initialize(sContext);
        MVUtils.getInstance();
        //查看缓存文件
/*        String initialize = MMKV.initialize(this);
        System.out.println("MMKV INIT " + initialize);*/

        //创建本地数据库
        db = AppDatabase.getInstance(sContext);
    }

    public static Context getContext() {
        return sContext;
    }

    public static AppDatabase getDb(){
        return db;
    }
}
