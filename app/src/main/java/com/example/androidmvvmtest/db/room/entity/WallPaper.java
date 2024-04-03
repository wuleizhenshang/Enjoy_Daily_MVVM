package com.example.androidmvvmtest.db.room.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe:
 */
@Entity(tableName = "wallpaper")
public class WallPaper {

    @PrimaryKey(autoGenerate = true)
    private int uid = 0;

    private String img;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public WallPaper() {}

    @Ignore
    public WallPaper(String img) {
        this.img = img;
    }
}

