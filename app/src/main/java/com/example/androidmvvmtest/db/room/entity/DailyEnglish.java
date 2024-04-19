package com.example.androidmvvmtest.db.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/15 20:09
 * @Description: 每日英语
 */
@Entity
public class DailyEnglish {
    @PrimaryKey(autoGenerate = true)
    private int id;//id
    private String englishContent;//具体英文内容
    private String chineseContent;//具体中文内容（翻译）
    private String date;//日期
    private String source;//来源

    public DailyEnglish() {
    }

    @Ignore
    public DailyEnglish(String englishContent, String chineseContent, String date, String source) {
        this.englishContent = englishContent;
        this.chineseContent = chineseContent;
        this.date = date;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishContent() {
        return englishContent;
    }

    public void setEnglishContent(String englishContent) {
        this.englishContent = englishContent;
    }

    public String getChineseContent() {
        return chineseContent;
    }

    public void setChineseContent(String chineseContent) {
        this.chineseContent = chineseContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
