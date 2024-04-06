package com.example.androidmvvmtest.db.room.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androidmvvmtest.db.room.dao.ImageDao;
import com.example.androidmvvmtest.db.room.dao.NewsChannelDao;
import com.example.androidmvvmtest.db.room.dao.NewsDao;
import com.example.androidmvvmtest.db.room.dao.VideoDao;
import com.example.androidmvvmtest.db.room.dao.WallPaperDao;
import com.example.androidmvvmtest.db.room.entity.Image;
import com.example.androidmvvmtest.db.room.entity.News;
import com.example.androidmvvmtest.db.room.entity.NewsChannel;
import com.example.androidmvvmtest.db.room.entity.Video;
import com.example.androidmvvmtest.db.room.entity.WallPaper;

/**
 * @Author: wuleizhenshang
 * @Email: wuleizhenshang@163.com
 * @Date: 2024/03/16
 * @Discribe:
 */
@Database(entities = {Image.class, WallPaper.class, NewsChannel.class,News.class, Video.class}
        , version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "mvvm_demo";
    private static volatile AppDatabase mInstance;

    /**
     * 单例模式
     */
    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)//数据库版本迁移使用
                            .addMigrations(MIGRATION_2_3)
                            .addMigrations(MIGRATION_3_4)
                            .build();
                }
            }
        }
        return mInstance;
    }


    /**
     * 版本升级迁移1->2
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE `wallpaper` " +
                    "(uid INTEGER NOT NULL, " +
                    "img TEXT, " +
                    "PRIMARY KEY(`uid`))");
        }
    };

    /**
     * 版本迁移2->3
     */
    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE `news_channel` " +
                    "(id INTEGER NOT NULL, " +
                    "kind TEXT, " +
                    "time Long, " +
                    "PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE `news` " +
                    "(id INTEGER NOT NULL, " +
                    "channel TEXT, " +
                    "title TEXT, " +
                    "time TEXT, " +
                    "src TEXT, " +
                    "category TEXT, " +
                    "pic TEXT, " +
                    "url TEXT, " +
                    "content TEXT, " +
                    "PRIMARY KEY(`id`))");
        }
    };

    /**
     * 版本迁移3->4
     */
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE `video` " +
                    "(id INTEGER NOT NULL, " +
                    "title TEXT, " +
                    "share_url TEXT, " +
                    "author TEXT, " +
                    "item_cover TEXT, " +
                    "hot_value Integer, " +
                    "PRIMARY KEY(`id`))");
        }
    };

    public abstract ImageDao imageDao();

    public abstract WallPaperDao wallPaperDao();

    public abstract NewsChannelDao newsChannelDao();

    public abstract NewsDao newsDao();

    public abstract VideoDao videoDao();
}

