package com.example.androidmvvmtest.db.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/5 13:18
 * @Description: Video表
 */
@Entity(tableName = "video")
public class Video {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;//视频标题
    private String share_url;//视频播放页面URL
    private String author;//视频发布者昵称
    private String item_cover;//视频封面图
    private int hot_value;//热度指数
    private String hot_words;//视频热词

    //其他的播放数、点赞数、评论数就不保存了

    public Video() {
    }

    @Ignore
    public Video(String title, String share_url, String author, String item_cover, int hot_value, String hot_words) {
        this.title = title;
        this.share_url = share_url;
        this.author = author;
        this.item_cover = item_cover;
        this.hot_value = hot_value;
        this.hot_words = hot_words;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getItem_cover() {
        return item_cover;
    }

    public void setItem_cover(String item_cover) {
        this.item_cover = item_cover;
    }

    public int getHot_value() {
        return hot_value;
    }

    public void setHot_value(int hot_value) {
        this.hot_value = hot_value;
    }

    public String getHot_words() {
        return hot_words;
    }

    public void setHot_words(String hot_words) {
        this.hot_words = hot_words;
    }
}
