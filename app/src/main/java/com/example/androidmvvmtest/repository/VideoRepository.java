package com.example.androidmvvmtest.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.example.androidmvvmtest.base.BaseApplication;
import com.example.androidmvvmtest.db.room.entity.News;
import com.example.androidmvvmtest.db.room.entity.Video;
import com.example.androidmvvmtest.db.room.utils.CustomDisposable;
import com.example.androidmvvmtest.network.Interface.IVideo;
import com.example.androidmvvmtest.network.api.NetworkApi;
import com.example.androidmvvmtest.network.bean.response.VideoResponseBean;
import com.example.androidmvvmtest.network.observer.BaseObserver;
import com.example.androidmvvmtest.utils.Constant;
import com.example.androidmvvmtest.utils.DateUtil;
import com.example.androidmvvmtest.utils.KLog;
import com.example.androidmvvmtest.utils.MVUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/6 16:28
 * @Description: 热门视频数据仓库
 */
public class VideoRepository {
    MutableLiveData<List<VideoResponseBean.ResultDTO>> videoList = new MutableLiveData<>();//保存数据
    MutableLiveData<String> errorMsg = new MutableLiveData<>();//保存错误信息

    /**
     * 获取视频数据
     *
     * @return 数据
     */
    public MutableLiveData<List<VideoResponseBean.ResultDTO>> getVideoList() {
        if (MVUtils.getBoolean(Constant.IS_TODAY_REQUEST_VIDEO)) {
            if (DateUtil.getTimestamp() <= MVUtils.getLong(Constant.REQUEST_TIMESTAMP_VIDEO)) {
                getVideoListFromDB();
            } else {
                getVideoListFromInternet();
            }
        } else {
            getVideoListFromInternet();
        }
        return videoList;
    }

    /**
     * 从数据库获取数据
     */
    public void getVideoListFromDB() {
        List<VideoResponseBean.ResultDTO> list = new ArrayList<>();//临时保存数据
        Flowable<List<Video>> allVideo = BaseApplication.getDb().videoDao().getAllVideo();
        CustomDisposable.addDisposable(allVideo, new Consumer<List<Video>>() {
            @Override
            public void accept(List<Video> vv) throws Exception {
                //处理数据
                for (Video bean : vv) {
                    list.add(new VideoResponseBean.ResultDTO(bean.getTitle(), bean.getShare_url(), bean.getAuthor(),
                            bean.getItem_cover(), bean.getHot_value(), bean.getHot_words()));
                }
                //返回数据
                videoList.postValue(list);
                KLog.i("TAGG","从本地数据库获取视频数据成功");
            }
        });
    }

    /**
     * 从网络获取数据
     */
    @SuppressLint("CheckResult")
    public void getVideoListFromInternet() {
        NetworkApi.createService(IVideo.class, NetworkApi.VIDEO).getVideoList(IVideo.KEY, IVideo.TYPE,50)
                .compose(NetworkApi.applySchedulers(new BaseObserver<VideoResponseBean>() {
                    @Override
                    public void onSuccess(VideoResponseBean videoResponseBean) {
                        if (videoResponseBean.getError_code() == 0) {
                            //保存到本地数据库
                            List<VideoResponseBean.ResultDTO> result = videoResponseBean.getResult();
                            addInternetVideoListToDB(result);
                            //更改数据
                            videoList.postValue(result);
                            KLog.i("TAGG","从网络获取视频数据成功");
                        } else {
                            errorMsg.postValue(videoResponseBean.getReason());
                            KLog.i("TAGG",videoResponseBean.getReason());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        errorMsg.postValue("请求热门视频错误：" + e.toString());
                    }
                }));
    }

    /**
     * 保存视频数据到数据库
     *
     * @param data 数据
     */
    public void addInternetVideoListToDB(List<VideoResponseBean.ResultDTO> data) {
        List<Video> list = new ArrayList<>();//临时保存数据
        Completable completable = BaseApplication.getDb().videoDao().deleteAllVideos();
        CustomDisposable.addDisposable(completable, new Action() {
            @Override
            public void run() throws Exception {
                //处理数据
                for (VideoResponseBean.ResultDTO bean : data) {
                    list.add(new Video(bean.getTitle(), bean.getShare_url(), bean.getAuthor(), bean.getItem_cover(),
                            bean.getHot_value(), bean.getHot_words()));
                }
                Completable completable1 = BaseApplication.getDb().videoDao().insertVideos(list);
                CustomDisposable.addDisposable(completable1, new Action() {
                    @Override
                    public void run() throws Exception {
                        MVUtils.put(Constant.IS_TODAY_REQUEST_VIDEO, true);
                        MVUtils.put(Constant.REQUEST_TIMESTAMP_VIDEO, DateUtil.getMillisNextEarlyMorning());
                        KLog.i("TAGG", "成功保存视频数据到数据库" + data.size() + "条");
                    }
                });
            }
        });
    }
}
