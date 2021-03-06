package com.example.zhmkaohe.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhmkaohe.R;
import com.example.zhmkaohe.adapter.DeskTopAdapter;

import com.example.zhmkaohe.adapter.FirstAdapter;
import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.bean.AppInfo;
import com.example.zhmkaohe.bean.AppList;
import com.example.zhmkaohe.bean.FirstInfo;
import com.example.zhmkaohe.util.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.hnshangyu.testgreendao.greendao.AppInfoDao;
import cn.hnshangyu.testgreendao.greendao.DaoSession;

public class SecondFragment extends Fragment {

    private List<AppInfo> appInfos;
    private List<AppInfo> Infos = new ArrayList<>();
    private List<AppInfo> applist = new ArrayList<>();
    private RecyclerView mGridView;
    private int pos;
    private DeskTopAdapter deskTopAdapter;
    private String update_apps_list;
    private Boolean update_flag;
    private AppInfoDao appInfoDao;
    private List<AppInfo> list1;
    private AppInfo appInfo2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment2, container, false);
        mGridView = inflate.findViewById(R.id.mGridView);
        DaoSession daoSession = MyApp.getDaoSession();
        appInfoDao = daoSession.getAppInfoDao();
        update_flag = (Boolean) SharedPreferencesUtils.getParam(getContext(), "update_flag", false);
        update_apps_list = (String) SharedPreferencesUtils.getParam(getContext(), "update_apps_list", "");
        Bundle arguments = getArguments();
        pos = arguments.getInt("position");
        initAppList();
        return inflate;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.i("TestData", "FoundFragment 加载请求网络数据");
            //TO-DO 执行网络数据请求

        }


    }

    public List<AppList> getList(String json) {
        List<AppList> lists = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject != null) {
                    String appName = jsonObject.optString("appName");
                    String packName = jsonObject.optString("packName");
                    String version = jsonObject.optString("version");
                    Boolean forceUpdate = jsonObject.optBoolean("forceUpdate");
                    AppList appList = new AppList(appName, packName, version, forceUpdate);
                    lists.add(appList);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }

    //初始化数据 添加数据库 展示
    private void initAppList() {
        //appInfoDao.deleteAll();
        //获取桌面的所有应用
        appInfos = MyApp.GetAppList1(getContext());
        //查询数据库中所有数据
        list1 = appInfoDao.queryBuilder().orderAsc(AppInfoDao.Properties.Type).list();
        //获取卸载的应用包名
        String rename = (String) SharedPreferencesUtils.getParam(getContext(), "rename", "");
        //获取安装的应用包名
        String addname = (String) SharedPreferencesUtils.getParam(getContext(), "addname", "");
        Log.e("安装", addname);
        Log.e("卸载", rename);
        Log.e("sss", list1.size() + "");
        //判断数据库中是否有数据
        if (list1.size() == 0) {
            //没有初始化数据添加到数据库
            for (int i = 0; i < appInfos.size(); i++) {
                appInfos.get(i).setType(i);
            }
            Log.e("aaaaaaaa", appInfos.toString());
            for (int i = 0; i < appInfos.size(); i++) {
                appInfoDao.insert(appInfos.get(i));
            }
            //展示数据列表
            initdata(appInfos);
        } else {
            //有数据直接展示数据库中数据
            applist.clear();
            //判断安装应用包名是否为空
            if (!addname.equals("")) {
                //判断数据库中是否已安装（true 安装 false 不安装）
                boolean isadd = true;
                for (int i = 0; i < list1.size(); i++) {
                    if (addname.equals(list1.get(i).getPackageName())) {
                        isadd = false;
                    }
                }
                //true 安装应用
                if (isadd) {
                    //循环桌面应用 与安装包名一致添加数据库
                    for (int i = 0; i < appInfos.size(); i++) {
                        if (addname.equals(appInfos.get(i).getPackageName())) {
                            appInfo2 = new AppInfo();
                            appInfo2.setType(appInfos.size() - 1);
                            appInfo2.setName(appInfos.get(i).getName());
                            appInfo2.setPackageName(appInfos.get(i).getPackageName());
                            //appInfo2.setIco(appInfos.get(i).getIco());
                        }
                    }
                    //添加数据库
                    appInfoDao.insert(appInfo2);
                }
            }

            //判断卸载包名是否为空
            if (!rename.equals("")) {
                //不为空循环数据库 与包名一致删除此条数据
                for (int i = 0; i < list1.size(); i++) {
                    if (rename.equals(list1.get(i).getPackageName())) {
                        appInfoDao.delete(list1.get(i));
                    }
                }

                // appInfoDao.updateInTx(list1);
            }


            //循环数据库中数据 创建新对象添加图标列表
            for (int i = 0; i < list1.size(); i++) {
                AppInfo appInfo1 = new AppInfo();
                appInfo1.setType(list1.get(i).getType());
                appInfo1.setName(list1.get(i).getName());
                appInfo1.setPackageName(list1.get(i).getPackageName());
                for (int j = 0; j < appInfos.size(); j++) {
                    if (list1.get(i).getName().equals(appInfos.get(j).getName())) {
                        //AppInfo appInfo = appInfos.get(j);
                        appInfo1.setIco(appInfos.get(j).getIco());

                    }
                }
                applist.add(appInfo1);

            }
            //展示数据
            initdata(applist);
        }


    }

    //展示数据列表
    private void initdata(List<AppInfo> appInfos) {
        Infos.clear();
        //根据传过来的pos 添加每页展示的六条数据
        for (int i = (pos - 1) * 6; i < (pos - 1) * 6 + 6; i++) {
            if (i < appInfos.size()) {
                Infos.add(appInfos.get(i));
            }
        }

        Log.e("dddddd", Infos.size() + "");
        //判断接收的广播是否有更新
        if (update_flag) {
            //true 根据传过来列表 添加显示更新的图标
            List<AppList> list = getList(update_apps_list);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < Infos.size(); j++) {
                    if (list.get(i).getAppName().equals(Infos.get(j).getName())) {
                        Infos.get(j).setUpdate_flag(true);
                    }
                }
            }
        }
        //展示数据列表
        deskTopAdapter = new DeskTopAdapter(Infos, getContext());
        mGridView.setAdapter(deskTopAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        mGridView.setLayoutManager(gridLayoutManager);
        //拖拽排序刷新列表
        new ItemTouchHelper(mCallback).attachToRecyclerView(mGridView);


    }

    //拖拽排序 刷新数据库
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0) {
        //drag方向，长按振动后可以拖动0//swip方向，实现滑动删除

        /**
         * @param recyclerView
         * @param viewHolder 拖动的ViewHolder
         * @param target 目标位置的ViewHolder
         * @return
         */
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                //分别把中间所有的item的位置重新交换
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(Infos, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(Infos, i, i - 1);
                }
            }
            //根据拖拽下标的转换 更新数据库
            List<AppInfo> list = appInfoDao.queryBuilder().build().list();
            Log.e("aaaa", list.size() + "");
            list1.get((pos - 1) * 6 + toPosition).setType((pos - 1) * 6 + fromPosition);
            list1.get((pos - 1) * 6 + fromPosition).setType((pos - 1) * 6 + toPosition);
            //更新数据库
            appInfoDao.updateInTx(list1);
            List<AppInfo> list1 = appInfoDao.queryBuilder().build().list();
            Log.e("aaaaaa", list1.size() + "");
            //刷新列表
            deskTopAdapter.notifyItemMoved(fromPosition, toPosition);

            //返回true表示执行拖动
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Infos.remove(position);
            deskTopAdapter.notifyItemRemoved(position);
        }

    };


}
