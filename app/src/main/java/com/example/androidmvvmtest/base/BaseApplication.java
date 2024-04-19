package com.example.androidmvvmtest.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.androidmvvmtest.db.room.database.AppDatabase;
import com.example.androidmvvmtest.ui.activity.ActivityManager;
import com.example.androidmvvmtest.utils.MVUtils;
import com.example.androidmvvmtest.utils.ScreenUtil;
import com.example.androidmvvmtest.utils.ToastUtil;
import com.tencent.mmkv.MMKV;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

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

        //MMKV初始化
        MMKV.initialize(sContext);
        MVUtils.getInstance();
        //查看缓存文件
/*        String initialize = MMKV.initialize(this);
        System.out.println("MMKV INIT " + initialize);*/

        //创建本地数据库
        db = AppDatabase.getInstance(sContext);
        //腾讯WebView初始化
        initX5WebView();
    }

    public static Context getContext() {
        return sContext;
    }

    public static AppDatabase getDb(){
        return db;
    }

    /**
     * 初始化X5WebView
     */
    private void initX5WebView() {
        HashMap map = new HashMap(2);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.i("TAGG"," onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static ActivityManager getActivityManager() {
        return ActivityManager.getInstance();
    }

}
