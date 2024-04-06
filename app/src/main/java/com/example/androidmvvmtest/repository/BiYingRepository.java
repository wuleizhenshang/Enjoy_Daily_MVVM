package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.db.room.entity.WallPaper;
import com.example.androidmvvmtest.network.bean.response.BiYingResponse;
import com.example.androidmvvmtest.network.bean.response.WallPaperResponse;
import com.example.androidmvvmtest.network.Interface.IBingPic;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.base.BaseApplication;
import com.example.androidmvvmtest.db.room.entity.Image;
import com.example.androidmvvmtest.db.room.utils.CustomDisposable;
import com.example.androidmvvmtest.utils.DateUtil;
import com.example.androidmvvmtest.utils.KLog;
import com.example.androidmvvmtest.utils.MVUtils;
import com.example.androidmvvmtest.utils.Constant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/15
 * @Discribe:
 */
public class BiYingRepository {

    /**
     * 每日一图
     */
    final MutableLiveData<BiYingResponse> biyingImage = new MutableLiveData<>();

    /**
     * 获取每日一图数据
     * @return 返回数据
     */
    @SuppressLint("CheckResult")
    public MutableLiveData<BiYingResponse> getBiYingData() {
        //今日此接口是否已请求
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP)) {
                //当前时间未超过次日0点，从本地获取
                getLocalDB();
            } else {
                //大于则数据需要更新，从网络获取
                requestNetworkApi();
            }
        } else {
            //没有请求过接口 或 当前时间，从网络获取
            requestNetworkApi();
        }
        return biyingImage;
    }

    /**
     * 从网络上请求每日一图数据
     */
    @SuppressLint("CheckResult")
    private void requestNetworkApi() {
        KLog.i("TAGG", "从网络获取每日一图");
        IBingPic service = NetworkApi.createService(IBingPic.class, 0);
        service.biying().compose(NetworkApi.applySchedulers(new BaseObserver<BiYingResponse>() {
            @Override
            public void onSuccess(BiYingResponse biYingImgResponse) {
                //存储到本地数据库中，并记录今日已请求了数据
                saveImageData(biYingImgResponse);
                biyingImage.setValue(biYingImgResponse);
            }

            @Override
            public void onFailure(Throwable e) {
                KLog.e("BiYing Error: " + e.toString());
            }
        }));
    }

    /**
     * 保存图片数据到本地数据库
     */
    private void saveImageData(BiYingResponse biYingImgResponse) {
        //记录今日已请求
        MVUtils.put(Constant.IS_TODAY_REQUEST, true);
        //记录此次请求的时最晚有效时间戳
        MVUtils.put(Constant.REQUEST_TIMESTAMP, DateUtil.getMillisNextEarlyMorning());
        BiYingResponse.ImagesBean bean = biYingImgResponse.getImages().get(0);
        //保存到数据库，使用rxjava
        CustomDisposable.addDisposable(BaseApplication.getDb().imageDao().insertAll(new Image(1, bean.getUrl(), bean.getUrlbase(), bean.getCopyright(), bean.getCopyrightlink(), bean.getTitle())), new Action() {
            @Override
            public void run() throws Exception {
                KLog.i("TAGG", "保存必应每日一图到本地成功！");
            }
        });
    }

    /**
     * 从本地数据库获取图片数据
     */
    private void getLocalDB() {
        KLog.i("TAGG", "从本地数据库获取每日一图");
        BiYingResponse biYingImgResponse = new BiYingResponse();

        //使用rxjava
        CustomDisposable.addDisposable(BaseApplication.getDb().imageDao().queryById(1), new Consumer<Image>() {
            @Override
            public void accept(Image image) throws Exception {
                BiYingResponse.ImagesBean imagesBean = new BiYingResponse.ImagesBean();
                imagesBean.setUrl(image.getUrl());
                imagesBean.setUrlbase(image.getUrlbase());
                imagesBean.setCopyright(image.getCopyright());
                imagesBean.setCopyrightlink(image.getCopyrightlink());
                imagesBean.setTitle(image.getTitle());
                List<BiYingResponse.ImagesBean> imagesBeanList = new ArrayList<>();
                imagesBeanList.add(imagesBean);
                biYingImgResponse.setImages(imagesBeanList);
                biyingImage.postValue(biYingImgResponse);
            }
        });
    }

    /**
     * 热门壁纸数据
     */
    final MutableLiveData<WallPaperResponse> wallPaper = new MutableLiveData<>();

    /**
     * 获取壁纸数据
     *
     * @return wallPaper
     */
    @SuppressLint("CheckResult")
    public LiveData<WallPaperResponse> getWallPaper() {
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_WALLPAPER)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_WALLPAPER)) {
                getWallPaperFromDB();
            } else {
                getWallPaperFromInternet();
            }
        } else {
            getWallPaperFromInternet();
        }
        return wallPaper;
    }

    /**
     * 从网络获取热门壁纸
     */
    @SuppressLint("CheckResult")
    private void getWallPaperFromInternet() {
        KLog.i("TAGG", "从网络获取热门壁纸");
        NetworkApi.createService(IBingPic.class, 1).wallPaper().compose(NetworkApi.applySchedulers(new BaseObserver<WallPaperResponse>() {
            @Override
            public void onSuccess(WallPaperResponse wallPaperResponse) {
                KLog.e("WallPaper: " + new Gson().toJson(wallPaperResponse));
                saveWallPaper(wallPaperResponse);
                wallPaper.setValue(wallPaperResponse);
            }

            @Override
            public void onFailure(Throwable e) {
                KLog.e("WallPaper Error: " + e.toString());
            }
        }));
    }

    /**
     * 保存壁纸数据到数据库
     *
     * @param wallPaperResponse 网络请求的响应数据
     */
    private void saveWallPaper(WallPaperResponse wallPaperResponse) {
        MVUtils.put(Constant.IS_TODAY_REQUEST_WALLPAPER, true);
        MVUtils.put(Constant.REQUEST_TIMESTAMP_WALLPAPER, DateUtil.getMillisNextEarlyMorning());

        Completable deleteAll = BaseApplication.getDb().wallPaperDao().deleteAll();
        CustomDisposable.addDisposable(deleteAll, new Action() {
            @Override
            public void run() throws Exception {
                KLog.i("TAGG", "删除热门壁纸数据成功！");
                List<WallPaper> list = new ArrayList<>();
                for (WallPaperResponse.ResBean.VerticalBean bean : wallPaperResponse.getRes().getVertical()) {
                    list.add(new WallPaper(bean.getImg()));
                }

                //保存到数据库
                Completable insertAll = BaseApplication.getDb().wallPaperDao().insertAll(list);
                CustomDisposable.addDisposable(insertAll, new Action() {
                    @Override
                    public void run() throws Exception {
                        KLog.i("TAGG", "保存热门壁纸数据成功！保存" + list.size() + "条数据");
                    }
                });
            }
        });
    }

    /**
     * 从数据库读取壁纸数据
     */
    private void getWallPaperFromDB() {
        KLog.i("TAGG", "从本地数据库获取热门壁纸");
        WallPaperResponse wallPaperResponse = new WallPaperResponse();
        WallPaperResponse.ResBean resBean = new WallPaperResponse.ResBean();
        List<WallPaperResponse.ResBean.VerticalBean> verticalBeanList = new ArrayList<>();
        Flowable<List<WallPaper>> listFlowable = BaseApplication.getDb().wallPaperDao().getAll();
        CustomDisposable.addDisposable(listFlowable, wallPapers -> {
            for (WallPaper paper : wallPapers) {
                WallPaperResponse.ResBean.VerticalBean verticalBean = new WallPaperResponse.ResBean.VerticalBean();
                verticalBean.setImg(paper.getImg());
                verticalBeanList.add(verticalBean);
            }
            resBean.setVertical(verticalBeanList);
            wallPaperResponse.setRes(resBean);
            wallPaper.postValue(wallPaperResponse);
        });
    }

}
