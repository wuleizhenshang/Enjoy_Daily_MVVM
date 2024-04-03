package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.network.bean.response.NewsTitleResponseBean;
import com.example.androidmvvmtest.network.bean.response.Result;
import com.example.androidmvvmtest.network.Interface.INews;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.utils.KLog;
import com.google.gson.Gson;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe: 新闻相关的存储库
 */
public class NewsRepository {

    private INews mINews;

    /**
     * 获取新闻具体标题分类
     *
     * @return
     */
    @SuppressLint("CheckResult")
    public MutableLiveData<List<String>> getNewsTitle() {
        final MutableLiveData<List<String>> newsTitleList = new MutableLiveData<>();
        mINews = NetworkApi.createService(INews.class);
        mINews.getNewsTitleList(INews.KEY).compose(NetworkApi.applySchedulers(new BaseObserver<Result<NewsTitleResponseBean>>() {
            @Override
            public void onSuccess(Result<NewsTitleResponseBean> newsTitleResponseBeanResult) {
                KLog.i(new Gson().toJson(newsTitleResponseBeanResult));
                newsTitleList.setValue(newsTitleResponseBeanResult.getData().getList());
            }

            @Override
            public void onFailure(Throwable e) {
                KLog.i(e.toString());
            }
        }));
        return newsTitleList;
    }

    /**
     * 获取新闻
     *
     * @param name
     * @param num
     * @param start
     * @return
     */
    @SuppressLint("CheckResult")
    public MutableLiveData<List<NewsResponseBean.NewsBean>> getNewsList(String name,
                                                                        int num, int start) {
        final MutableLiveData<List<NewsResponseBean.NewsBean>> data = new MutableLiveData<>();
        mINews = NetworkApi.createService(INews.class);
        mINews.getNewsList(INews.KEY, name, num, start).compose(NetworkApi.applySchedulers(new BaseObserver<Result<NewsResponseBean>>() {
            @Override
            public void onSuccess(Result<NewsResponseBean> newsResponseBeanResult) {
                KLog.i(new Gson().toJson(newsResponseBeanResult));
                data.setValue(newsResponseBeanResult.getData().getList());
            }

            @Override
            public void onFailure(Throwable e) {
                KLog.i(e.toString());
            }
        }));
        return data;
    }
}
