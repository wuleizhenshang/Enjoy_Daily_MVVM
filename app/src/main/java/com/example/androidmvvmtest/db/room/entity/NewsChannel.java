package com.example.androidmvvmtest.db.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author wuleizhenshang
 * @Email wuleizhenshang@163.com
 * @Date 2024/4/5 13:20
 * @Description: 新闻频道数据表
 */
@Entity(tableName = "news_channel")
public class NewsChannel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String kind;

    private long time;//下次需要更新的时间

    public NewsChannel() {
    }

    @Ignore
    public NewsChannel(String kind, long time) {
        this.kind = kind;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
