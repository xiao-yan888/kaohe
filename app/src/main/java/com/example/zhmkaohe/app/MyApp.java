package com.example.zhmkaohe.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.zhmkaohe.bean.AppInfo;
import com.example.zhmkaohe.util.UnCeHandler;

import java.util.ArrayList;
import java.util.List;

import cn.hnshangyu.testgreendao.greendao.DaoMaster;
import cn.hnshangyu.testgreendao.greendao.DaoSession;


public class MyApp extends Application {
    private static DaoSession daoSession;

    ArrayList<Activity> list = new ArrayList<Activity>();

    public void init(){
        //设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public void removeActivity(Activity a){
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象*/
    public void addActivity(Activity a){
        list.add(a);
    }

    /**
     * 关闭Activity列表中的所有Activity*/
    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "kaohe.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
        daoSession = daoMaster.newSession();
    }
    public static DaoSession getDaoSession(){
        return daoSession;
    }


    public static List<AppInfo> GetAppList1(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);

        for (int i = 0; i < packages.size(); i++) {
            if ((packages.get(i).applicationInfo.flags & packages.get(i).applicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                PackageInfo packageInfo = packages.get(i);
                AppInfo tmpInfo = new AppInfo();
                tmpInfo.setVersionName(packageInfo.versionName);
                tmpInfo.setName(packageInfo.applicationInfo.loadLabel(pm).toString());
                tmpInfo.setIco(packageInfo.applicationInfo.loadIcon(pm));
                tmpInfo.setPackageName(packageInfo.packageName);
                list.add(tmpInfo);
            }
        }
        return list;
    }

}
