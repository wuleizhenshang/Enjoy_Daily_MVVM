package com.example.androidmvvmtest.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidmvvmtest.network.bean.response.VideoResponseBean;
import com.example.androidmvvmtest.repository.VideoRepository;

import java.util.List;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/3 23:47
 * @Description: 视频数据ViewModel
 */
public class VideoViewModel extends ViewModel {
    MutableLiveData<List<VideoResponseBean.ResultDTO>> videoList = new MutableLiveData<>();//保存数据
    MutableLiveData<String> errorMsg = new MutableLiveData<>();//保存错误信息

    public MutableLiveData<List<VideoResponseBean.ResultDTO>> getVideoList() {
        videoList = new VideoRepository().getVideoList();
        return videoList;
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }
}
