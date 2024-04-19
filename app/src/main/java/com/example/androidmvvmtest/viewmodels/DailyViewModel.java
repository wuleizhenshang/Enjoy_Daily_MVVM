package com.example.androidmvvmtest.viewmodels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.R;
import com.example.androidmvvmtest.base.BaseApplication;
import com.example.androidmvvmtest.db.room.entity.DailyContent;
import com.example.androidmvvmtest.db.room.entity.DailyEnglish;
import com.example.androidmvvmtest.repository.DailyRepository;
import com.example.androidmvvmtest.ui.fragment.DailyContentFragment;
import com.example.androidmvvmtest.ui.fragment.DailyEnglishFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 20:55
 * @Description: TODO
 */
public class DailyViewModel extends ViewModel {
    private MutableLiveData<DailyEnglish> dailyEnglish = new MutableLiveData<>();
    private MutableLiveData<String> dailyEnglishError = new MutableLiveData<>();
    private boolean isRequestData = false;
    private DailyRepository mDailyRepository = new DailyRepository();
    private MutableLiveData<String[]> tabs = new MutableLiveData<>();//tabs
    private MutableLiveData<List<Fragment>>fragments = new MutableLiveData<>();//fragments

    /**
     * 获取请求的错误信息
     * @return msg
     */
    public MutableLiveData<String> getDailyEnglishError() {
        if (isRequestData){
            return mDailyRepository.getDailyEnglishError();
        }
        return null;
    }

    /**
     * 获取每日英文数据
     * @return data
     */
    public MutableLiveData<DailyEnglish> getDailyEnglish() {
        isRequestData = true;
        return mDailyRepository.getDailyEnglish();
    }

    //--------------------------------------------------------
    /**
     * 获取tabs
     * @return data
     */
    public MutableLiveData<String[]> getTabs() {
        tabs.postValue(new String[]{
                BaseApplication.sContext.getString(R.string.daily_english_toolbar_title),
                BaseApplication.sContext.getString(R.string.daily_content_toolbar_title),
        });
        return tabs;
    }

    /**
     * 获取fragments
     * @return data
     */
    public MutableLiveData<List<Fragment>> getFragments() {
        List<Fragment>list = new ArrayList<>();
        list.add(new DailyEnglishFragment());
        list.add(new DailyContentFragment());
        fragments.postValue(list);
        return fragments;
    }

    //--------------------------------------------------------

    private boolean isRequestDailyContentData = false;

    /**
     * 获取每日一文数据
     * @return data
     */
    public MutableLiveData<DailyContent> getDailyContent(){
        isRequestDailyContentData = true;
        return mDailyRepository.getDailyContent();
    }

    /**
     * 获取每日一文请求的错误信息
     * @return data
     */
    public MutableLiveData<String> getDailyContentErrorMSG(){
        if (isRequestDailyContentData){
            return mDailyRepository.getDailyContentErrorMSG();
        }
        return null;
    }

}
