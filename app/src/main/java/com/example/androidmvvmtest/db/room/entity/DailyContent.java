package com.example.androidmvvmtest.db.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/17 8:53
 * @Description: TODO
 */
@Entity
public class DailyContent {
    @PrimaryKey(autoGenerate = true)
    private int id;//id
    private String content;//内容
    private String src;//来源
    private String imgUrl;//图片链接
    private String imgAuthor;//图片作者
    private String date;//日期

    public DailyContent() {
    }

    @Ignore
    public DailyContent(String content, String src, String imgUrl, String imgAuthor, String date) {
        this.content = content;
        this.src = src;
        this.imgUrl = imgUrl;
        this.imgAuthor = imgAuthor;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgAuthor() {
        return imgAuthor;
    }

    public void setImgAuthor(String imgAuthor) {
        this.imgAuthor = imgAuthor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
