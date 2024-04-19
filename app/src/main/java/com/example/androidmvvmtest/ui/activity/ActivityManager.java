package com.example.androidmvvmtest.ui.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/14 17:44
 * @Description: TODO
 */
public class ActivityManager {

    //保存所有创建的Activity
    private final List<Activity> activityList = new ArrayList<>();

    public static ActivityManager mInstance;

    public static ActivityManager getInstance() {
        if (mInstance == null) {
            synchronized (ActivityManager.class) {
                if (mInstance == null) {
                    mInstance = new ActivityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 添加Activity
     * @param activity
     */
    public void addActivity(Activity activity){
        if(activity != null){
            activityList.add(activity);
        }
    }

    /**
     * 移除Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(activity != null){
            activityList.remove(activity);
        }
    }

    /**
     * 关闭所有Activity
     */
    public void finishAllActivity(){
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}

