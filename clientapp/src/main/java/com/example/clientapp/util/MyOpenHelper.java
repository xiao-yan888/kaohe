package com.example.clientapp.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.clientapp.bean.DataInfo;

import cn.hnshangyu.testgreendao.greendao.DaoMaster;
import cn.hnshangyu.testgreendao.greendao.DataInfoDao;

public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        // 迁移数据库(如果修改了多个实体类，则需要把对应的Dao都传进来)
        if (oldVersion < newVersion) {
            MigrationHelper.migrate(db, DataInfoDao.class);
        }
    }
}
