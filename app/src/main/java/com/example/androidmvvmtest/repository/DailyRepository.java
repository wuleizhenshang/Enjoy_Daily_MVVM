package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.base.BaseActivity;
import com.example.androidmvvmtest.base.BaseApplication;
import com.example.androidmvvmtest.db.room.entity.DailyContent;
import com.example.androidmvvmtest.db.room.entity.DailyEnglish;
import com.example.androidmvvmtest.db.room.entity.Image;
import com.example.androidmvvmtest.db.room.utils.CustomDisposable;
import com.example.androidmvvmtest.network.Interface.IDaily;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.bean.response.DailyContentResponseBean;
import com.example.androidmvvmtest.network.bean.response.DailyEnglishResponseBean;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.DateUtil;
import com.example.androidmvvmtest.utils.KLog;
import com.example.androidmvvmtest.utils.MVUtils;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 20:28
 * @Description: 每日一文和每日英语的数据仓库
 */
public class DailyRepository {
    private MutableLiveData<DailyEnglish> dailyEnglish = new MutableLiveData<>();
    private MutableLiveData<String> dailyEnglishError = new MutableLiveData<>();

    /**
     * 返回获取每日英语的具体获取信息
     *
     * @return msg
     */
    public MutableLiveData<String> getDailyEnglishError() {
        return dailyEnglishError;
    }

    /**
     * 获取每日英语数据
     *
     * @return data
     */
    public MutableLiveData<DailyEnglish> getDailyEnglish() {
        //今日此接口是否已请求
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_DAILY_ENGLISH)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_DAILY_ENGLISH)) {
                //当前时间未超过次日0点，从本地获取
                getDailyEnglishFromDB();
            } else {
                //大于则数据需要更新，从网络获取
                getDailyEnglishFromInternet();
            }
        } else {
            //没有请求过接口 或 当前时间，从网络获取
            getDailyEnglishFromInternet();
        }
        return dailyEnglish;
    }

    /**
     * 从本地数据库获取每日英语数据
     */
    private void getDailyEnglishFromDB() {
        Flowable<DailyEnglish> dailyEnglishFlowable = BaseApplication.getDb().dailyEnglishDao().getDailyEnglish();
        CustomDisposable.addDisposable(dailyEnglishFlowable, new Consumer<DailyEnglish>() {
            @Override
            public void accept(DailyEnglish dailyEnglish1) throws Exception {
                dailyEnglish.postValue(dailyEnglish1);
            }
        });
    }

    /**
     * 从网络获取每日英语数据
     */
    @SuppressLint("CheckResult")
    private void getDailyEnglishFromInternet() {
        NetworkApi.createService(IDaily.class, NetworkApi.DAILY).getDailyEnglish()
                .compose(NetworkApi.applySchedulers(new BaseObserver<DailyEnglishResponseBean>() {
                    @Override
                    public void onSuccess(DailyEnglishResponseBean dailyEnglishResponseBean) {
                        if (dailyEnglishResponseBean != null && dailyEnglishResponseBean.getCode() == 200) {
                            //获取到数据
                            DailyEnglishResponseBean.Result result = dailyEnglishResponseBean.getResult();
                            DailyEnglish data = new DailyEnglish(
                                    result.getContent(), result.getNote(), result.getDate(),
                                    result.getSource()
                            );
                            dailyEnglish.postValue(data);
                            //保存到本地数据库
                            saveDailyEnglishToDB(data);
                        } else {
                            if (dailyEnglishResponseBean != null && dailyEnglishResponseBean.getMsg() != null) {
                                dailyEnglishError.postValue(dailyEnglishResponseBean.getMsg());
                                KLog.i("TAGG", dailyEnglishResponseBean.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        dailyEnglishError.postValue(e.toString());
                        KLog.i("TAGG", e.toString());
                    }
                }));
    }

    /**
     * 保存从网络获取到的数据到本地数据库
     *
     * @param dailyEnglish data
     */
    private void saveDailyEnglishToDB(DailyEnglish dailyEnglish) {
        //删除
        Completable deleteAll = BaseApplication.getDb().dailyEnglishDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, new Action() {
            @Override
            public void run() throws Exception {
                //添加数据
                Completable insert = BaseApplication.getDb().dailyEnglishDao().insert(dailyEnglish);
                CustomDisposable.addDisposable(insert, new Action() {
                    @Override
                    public void run() throws Exception {
                        //保存数据到MMKV
                        MVUtils.put(Constant.IS_TODAY_REQUEST_DAILY_ENGLISH, true);
                        MVUtils.put(Constant.REQUEST_TIMESTAMP_DAILY_ENGLISH, DateUtil.getMillisNextEarlyMorning());
                        KLog.i("TAGG", "保存成功");
                    }
                });
            }
        });
    }

    //------------------------------------------------------------------

    private MutableLiveData<DailyContent> mDailyContentMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<String> mStringDailyContentMutableLiveData = new MutableLiveData<>();

    /**
     * 获取请求每日一文错误信息
     *
     * @return data
     */
    public MutableLiveData<String> getDailyContentErrorMSG() {
        return mStringDailyContentMutableLiveData;
    }

    /**
     * 获取每日一文数据
     *
     * @return data
     */
    public MutableLiveData<DailyContent> getDailyContent() {
        //今日此接口是否已请求
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_DAILY_CONTENT)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_DAILY_CONTENT)) {
                //当前时间未超过次日0点，从本地获取
                getDailyContentFromDB();
            } else {
                //大于则数据需要更新，从网络获取
                getDailyContentFromInternet();
            }
        } else {
            //没有请求过接口 或 当前时间，从网络获取
            getDailyContentFromInternet();
        }
        return mDailyContentMutableLiveData;
    }

    /**
     * 从本地数据库获取每日一文
     */
    private void getDailyContentFromDB() {
        Flowable<DailyContent> dailyContent = BaseApplication.getDb().dailyContentDao().getDailyContent();
        CustomDisposable.addDisposable(dailyContent, new Consumer<DailyContent>() {
            @Override
            public void accept(DailyContent dailyContent) throws Exception {
                mDailyContentMutableLiveData.postValue(dailyContent);
                KLog.i("TAGG", "从数据库获取每日一文成功");
            }
        });
    }

    /**
     * 从网络获取每日一文数据
     */
    @SuppressLint("CheckResult")
    private void getDailyContentFromInternet() {

        NetworkApi.createService(IDaily.class, NetworkApi.DAILY).getDailyContent()
                .compose(NetworkApi.applySchedulers(new BaseObserver<DailyContentResponseBean>() {
                    @Override
                    public void onSuccess(DailyContentResponseBean dailyContentResponseBean) {
                        if (dailyContentResponseBean != null &&
                                dailyContentResponseBean.getCode() == 200) {
                            DailyContent dailyContent = new DailyContent(
                                    dailyContentResponseBean.getResult().getWord(),
                                    dailyContentResponseBean.getResult().getWordfrom(),
                                    dailyContentResponseBean.getResult().getImgurl(),
                                    dailyContentResponseBean.getResult().getImgauthor(),
                                    dailyContentResponseBean.getResult().getDate()
                            );
                            mDailyContentMutableLiveData.postValue(dailyContent);
                            //保存到本地
                            saveDailyContentToDB(dailyContent);
                            KLog.i("TAGG", "请求每日一文成功");
                        } else {
                            mStringDailyContentMutableLiveData.postValue("请求数据失败");
                            KLog.i("TAGG", "请求每日一文失败");
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        mStringDailyContentMutableLiveData.postValue(e.toString());
                        KLog.i("TAGG", "请求每日一文失败" + e);
                    }
                }));
    }

    /**
     * 保存每日一文数据到本地数据库
     *
     * @param dailyContent data
     */
    private void saveDailyContentToDB(DailyContent dailyContent) {
        Completable deleteAll = BaseApplication.getDb().dailyContentDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, new Action() {
            @Override
            public void run() throws Exception {
                Completable insert = BaseApplication.getDb().dailyContentDao().insert(dailyContent);
                CustomDisposable.addDisposable(insert, new Action() {
                    @Override
                    public void run() throws Exception {
                        //保存数据到MMKV
                        MVUtils.put(Constant.IS_TODAY_REQUEST_DAILY_CONTENT, true);
                        MVUtils.put(Constant.REQUEST_TIMESTAMP_DAILY_CONTENT, DateUtil.getMillisNextEarlyMorning());
                        KLog.i("TAGG", "保存成功");
                    }
                });
            }
        });
    }
}
