package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.base.BaseApplication;
import com.example.androidmvvmtest.db.room.entity.News;
import com.example.androidmvvmtest.db.room.entity.NewsChannel;
import com.example.androidmvvmtest.db.room.utils.CustomDisposable;
import com.example.androidmvvmtest.network.bean.response.NewsResponseBean;
import com.example.androidmvvmtest.network.bean.response.NewsTitleResponseBean;
import com.example.androidmvvmtest.network.bean.response.Result;
import com.example.androidmvvmtest.network.Interface.INews;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.DateUtil;
import com.example.androidmvvmtest.utils.KLog;
import com.example.androidmvvmtest.utils.MVUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe: 新闻相关的存储库，这是简单demo，先简化功能先，需要后面再补充，这里不做分类什么的先
 */
public class NewsRepository {
    public final MutableLiveData<List<String>> newsChannelList = new MutableLiveData<>();//保存新闻频道数据
    public final MutableLiveData<String> newsChannelError = new MutableLiveData<>();//保存新闻频道获取错误信息
    public final MutableLiveData<List<NewsResponseBean.NewsBean>> newsList = new MutableLiveData<>();//保存新闻数据
    public final MutableLiveData<String> newsError = new MutableLiveData<>();//保存新闻获取错误信息

    /**
     * 获取新闻分类
     *
     * @return 新闻分类
     */
    public MutableLiveData<List<String>> getNewsCategory() {
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_NEWS_CATEGORY)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_NEWS_CATEGORY)) {
                getNewsCategoryFromDB();
            } else {
                getNewsCategoryFromInternet();
            }
        } else {
            getNewsCategoryFromInternet();
        }
        return newsChannelList;
    }

    /**
     * 从数据库获取新闻分类数据
     */
    public void getNewsCategoryFromDB() {
        List<String> data = new ArrayList<>();//临时保存数据
        Flowable<List<NewsChannel>> flowable = BaseApplication.getDb().newsChannelDao().getAllNewsChannel();
        CustomDisposable.addDisposable(flowable, new Consumer<List<NewsChannel>>() {
            @Override
            public void accept(List<NewsChannel> newsChannels) throws Exception {
                //处理数据库拿到的数据
                for (NewsChannel newsChannel : newsChannels) {
                    data.add(newsChannel.getKind());
                }
                newsChannelList.postValue(data);
                KLog.i("TAGG","从本地数据库获取新闻分类数据成功");
            }
        });
    }

    /**
     * 从网络获取新闻分类数据
     */
    @SuppressLint("CheckResult")
    public void getNewsCategoryFromInternet() {
        NetworkApi.createService(INews.class, NetworkApi.NEWS).getNewsTitleList(INews.KEY).compose(NetworkApi.applySchedulers(new BaseObserver<Result<NewsTitleResponseBean>>() {
            @Override
            public void onSuccess(Result<NewsTitleResponseBean> newsTitleResponseBeanResult) {
                if (newsTitleResponseBeanResult.getCode() == 1) {
                    //保存到本地数据库
                    addInternetNewsCategoryToDB(newsTitleResponseBeanResult.getData().getList());
                    //更新数据
                    newsChannelList.postValue(newsTitleResponseBeanResult.getData().getList());
                    KLog.i("TAGG","从网络获取新闻分类数据成功");
                } else {
                    newsChannelError.postValue("请求频道错误：" + newsTitleResponseBeanResult.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                newsChannelError.postValue("请求频道错误：" + e.toString());
            }
        }));
    }

    /**
     * 保存从网络获取的新闻分类数据到本地
     *
     * @param data 数据
     */
    public void addInternetNewsCategoryToDB(List<String> data) {
        //清空数据库后保存
        Completable completable = BaseApplication.getDb().newsChannelDao().deleteAllNewsChannel();
        CustomDisposable.addDisposable(completable, new Action() {
            @Override
            public void run() throws Exception {
                //删除成功后处理数据并保存
                List<NewsChannel> list = new ArrayList<>();
                for (String s : data) {
                    NewsChannel newsChannel = new NewsChannel(s, DateUtil.getMillisNextEarlyMorning());
                    list.add(newsChannel);
                }
                Completable completable1 = BaseApplication.getDb().newsChannelDao().insertNewsChannel(list);
                CustomDisposable.addDisposable(completable1, new Action() {
                    @Override
                    public void run() throws Exception {
                        MVUtils.put(Constant.IS_TODAY_REQUEST_NEWS_CATEGORY, true);
                        MVUtils.put(Constant.REQUEST_TIMESTAMP_NEWS_CATEGORY, DateUtil.getMillisNextEarlyMorning());
                        KLog.i("TAGG", "成功保存" + list.size() + "新闻频道数据");
                    }
                });
            }
        });
    }

    /**
     * 获取新闻，需要使用频道名字后面再处理，这里都默认先
     *
     * @param name  频道名字
     * @param num   数量
     * @param start 开始位置
     * @return 数据
     */
    public MutableLiveData<List<NewsResponseBean.NewsBean>> getNews(String name, int num, int start) {
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_NEWS)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_NEWS)) {
                getNewsFromDB(name, num, start);
            } else {
                getNewsFromInternet(name, num, start);
            }
        } else {
            getNewsFromInternet(name, num, start);
        }
        return newsList;
    }

    /**
     * 从本地数据库获取新闻数据，这里先直接获取全部出来即可
     */
    public void getNewsFromDB(String name, int num, int start) {
        List<NewsResponseBean.NewsBean> list = new ArrayList<>();
        //从数据库获取数据并转换后返回
        Flowable<List<News>> flowable = BaseApplication.getDb().newsDao().getAllNews();
        CustomDisposable.addDisposable(flowable, new Consumer<List<News>>() {
            @Override
            public void accept(List<News> news) throws Exception {
                for (News bean : news) {
                    list.add(new NewsResponseBean.NewsBean(bean.getTitle(), bean.getTime(), bean.getSrc(),
                            bean.getCategory(), bean.getPic(), bean.getUrl(), bean.getUrl(), bean.getContent()));
                }
                newsList.postValue(list);
                KLog.i("TAGG","从本地数据库获取新闻数据成功");
            }
        });
    }

    /**
     * 从网络获取新闻数据
     */
    @SuppressLint("CheckResult")
    public void getNewsFromInternet(String name, int num, int start) {
        Observable<Result<NewsResponseBean>> list;
        if (name==null||name.equals("")){
           list = NetworkApi.createService(INews.class, NetworkApi.NEWS).getNewsList(INews.KEY, num, start);
        }else {
            list = NetworkApi.createService(INews.class, NetworkApi.NEWS).getNewsList(INews.KEY, name,num, start);
        }
        List<News> news = new ArrayList<>();//临时保存数据
        list.compose(NetworkApi.applySchedulers(new BaseObserver<Result<NewsResponseBean>>() {
            @Override
            public void onSuccess(Result<NewsResponseBean> newsResponseBeanResult) {
                if (newsResponseBeanResult.getCode() == 1) {
                    List<NewsResponseBean.NewsBean> temp = newsResponseBeanResult.getData().getList();
                    for (NewsResponseBean.NewsBean bean : temp) {
                        news.add(new News(bean.getTitle(), bean.getTime(), bean.getSrc(), bean.getCategory(), bean.getPic(), bean.getUrl(), bean.getContent()));
                    }
                    //保存到本地数据库
                    addInternetNewsToDB(news);
                    //更改数据通知
                    newsList.postValue(temp);
                    KLog.i("TAGG","从网络获取新闻分类数据成功");
                } else {
                    newsError.postValue("请求新闻数据错误：" + newsResponseBeanResult.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable e) {
                newsError.postValue("请求新闻数据错误：" + e.toString());
            }
        }));
    }

    /**
     * 保存新闻数据库到本地数据库
     *
     * @param data 数据
     */
    public void addInternetNewsToDB(List<News> data) {
        //清空数据库后开始保存
        Completable deleteAllNews = BaseApplication.getDb().newsDao().deleteAllNews();
        CustomDisposable.addDisposable(deleteAllNews, new Action() {
            @Override
            public void run() throws Exception {

                Completable completable = BaseApplication.getDb().newsDao().insertNews(data);
                CustomDisposable.addDisposable(completable, new Action() {
                    @Override
                    public void run() throws Exception {
                        //保存请求的时间
                        MVUtils.put(Constant.IS_TODAY_REQUEST_NEWS, true);
                        MVUtils.put(Constant.REQUEST_TIMESTAMP_NEWS, DateUtil.getMillisNextEarlyMorning());
                        KLog.i("TAGG", "保存新闻数据到数据库成功!" + data.size() + "条");
                    }
                });
            }
        });
    }
}
