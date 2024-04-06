package com.example.androidmvvmtest.db.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/5 13:18
 * @Description: 存储具体新闻的表
 */
@Entity(tableName = "news")
public class News {
    @PrimaryKey(autoGenerate = true)
    private int id;//id
    private String channel;//频道
    private String title;//标题
    private String time;//时间
    private String src;//来源
    private String category;//分类
    private String pic;//展示页面的图片
    private String url;//具体原文路径
    private String  Content;//内容

    public News() {
    }

    @Ignore
    public News(String channel, String title, String time, String src, String category, String pic, String url, String content) {
        this.channel = channel;
        this.title = title;
        this.time = time;
        this.src = src;
        this.category = category;
        this.pic = pic;
        this.url = url;
        Content = content;
    }

    //暂时使用这个，需要复杂的分类逻辑再加
    @Ignore
    public News( String title, String time, String src, String category, String pic, String url, String content) {
        this.title = title;
        this.time = time;
        this.src = src;
        this.category = category;
        this.pic = pic;
        this.url = url;
        Content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
