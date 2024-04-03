package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.repository.NewsRepository;

import java.util.List;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe: 新闻相关viewModel
 */
public class NewsViewModel extends ViewModel {

    //内容
    public String select;



    //新闻标题
    public MutableLiveData<List<String>> newsTitleList;

    public MutableLiveData<List<String>> getNewsTitleList() {
        if (newsTitleList==null){
            newsTitleList = new NewsRepository().getNewsTitle();
        }
        return newsTitleList;
    }

    //新闻详细信息
    public MutableLiveData<List<NewsResponseBean.NewsBean>> mNewsBean;

    public MutableLiveData<List<NewsResponseBean.NewsBean>> getNewsBean(String name, int num, int start) {
        return new NewsRepository().getNewsList(name, num, start);
    }
}
