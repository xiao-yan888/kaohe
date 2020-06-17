package com.example.clientapp.util;

import android.app.Application;

import cn.hnshangyu.testgreendao.greendao.DaoMaster;
import cn.hnshangyu.testgreendao.greendao.DaoSession;

public class MyApp extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "client.db");
        //创建数据库
        MyOpenHelper myOpenHelper = new MyOpenHelper(this, "client.db");
        DaoMaster daoMaster = new DaoMaster(myOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }
    public static DaoSession getDaoSession(){
        return daoSession;
    }

}
