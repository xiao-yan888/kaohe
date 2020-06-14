package com.example.zhmkaohe.util;

import android.content.Context;

import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.bean.AppInfo;
import com.example.zhmkaohe.bean.FirstInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.hnshangyu.testgreendao.greendao.DaoSession;
import cn.hnshangyu.testgreendao.greendao.FirstInfoDao;

public class DaoUtil {
    private static final boolean DUBUG = true;
    //private DaoManager manager;
    private FirstInfoDao infoDao;
    private DaoSession daoSession;

    public DaoUtil(Context context) {

        daoSession = MyApp.getDaoSession();
        //manager.setDebug(DUBUG);
    }

    /**
     * 添加数据，如果有重复则覆盖
     */
    public void insertInfo(FirstInfo firstInfo) {
        MyApp.getDaoSession().insertOrReplace(firstInfo);
    }

    /**
     * 添加多条数据，需要开辟新的线程
     */
    public void insertMultInfo(final List<FirstInfo> infos) {
        MyApp.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (FirstInfo info : infos) {
                    MyApp.getDaoSession().insertOrReplace(info);
                }
            }
        });
    }

    /**
     * 添加多条数据，需要开辟新的线程
     */
    public void insertMultAppInfo(final List<AppInfo> infos) {
        MyApp.getDaoSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (AppInfo info : infos) {
                    MyApp.getDaoSession().insertOrReplace(info);
                }
            }
        });
    }


    /**
     * 删除数据
     */
    public void deleteInfo(FirstInfo firstInfo) {
        MyApp.getDaoSession().delete(firstInfo);
    }

    /**
     * 删除全部数据
     */
    public void deleteAll() {
        MyApp.getDaoSession().getFirstInfoDao().deleteAll();
    }
    public void delete(){

    }
    /**
     * 更新数据
     */
    public void updateInfo(FirstInfo firstInfo) {
        MyApp.getDaoSession().update(firstInfo);
    }

    /**
     * 按照主键返回单条数据
     */
    public FirstInfo listOneInfo(long key) {
        return MyApp.getDaoSession().load(FirstInfo.class, key);
    }

    /**
     * 根据指定条件查询数据
     */
    public List<FirstInfo> queryInfo() {
        //查询构建器
        QueryBuilder<FirstInfo> builder = MyApp.getDaoSession().queryBuilder(FirstInfo.class);
        List<FirstInfo> list = builder.where(FirstInfoDao.Properties.Id.ge(1)).where(FirstInfoDao.Properties.Title.like("")).list();
        return list;
    }

    /**
     * 查询全部数据
     */
    public List<FirstInfo> queryAll() {
        return MyApp.getDaoSession().loadAll(FirstInfo.class);
    }
}
