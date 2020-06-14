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
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.zhmkaohe.DragGridView;
import com.example.zhmkaohe.R;
import com.example.zhmkaohe.adapter.DeskTopAdapter;

import com.example.zhmkaohe.adapter.FirstAdapter;
import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.bean.AppInfo;
import com.example.zhmkaohe.bean.AppList;
import com.example.zhmkaohe.bean.FirstInfo;
import com.example.zhmkaohe.util.SharedPreferencesUtils;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
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
    private List<AppInfo> Infos=new ArrayList<>();
    private List<AppInfo> applist=new ArrayList<>();
    //private DragGridView mGridView;
    private RecyclerView mGridView;
    private int pos;
    private DeskTopAdapter deskTopAdapter;
    private String update_apps_list;
    private Boolean update_flag;
    private AppInfoDao appInfoDao;
    private List<AppInfo> list1;

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

    public List<AppList> getList(String json){
        List<AppList> lists = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject!=null){
                    String appName = jsonObject.optString("appName");
                    String packName = jsonObject.optString("packName");
                    String version = jsonObject.optString("version");
                    Boolean forceUpdate = jsonObject.optBoolean("forceUpdate");
                    AppList appList = new AppList(appName,packName,version,forceUpdate);
                    lists.add(appList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }

    private void initAppList() {
       // GridView mGridView = getActivity().findViewById(R.id.mgv);
        list1 = appInfoDao.queryBuilder().orderAsc(AppInfoDao.Properties.Type).list();
        //List<AppInfo> list = appInfoDao.queryBuilder().orderAsc(AppInfoDao.Properties.Type).list();
        Log.e("sss", list1.size()+"");
        appInfos = MyApp.GetAppList1(getContext());
        for (int i = 0; i <appInfos.size() ; i++) {
            appInfos.get(i).setType(i);
        }
        if (list1.size()==0) {

            Log.e("aaaaaaaa", appInfos.toString());
            for (int i = 0; i <appInfos.size() ; i++) {
                appInfoDao.insert(appInfos.get(i));
            }
            initdata(appInfos);
        }else {
            applist.clear();
            for (int i = 0; i < list1.size() ; i++) {
                AppInfo appInfo1 = new AppInfo();
                appInfo1.setType(list1.get(i).getType());
                appInfo1.setName(list1.get(i).getName());
                for (int j = 0; j <appInfos.size() ; j++) {
                    if (list1.get(i).getName().equals(appInfos.get(j).getName())){
                        //AppInfo appInfo = appInfos.get(j);
                        appInfo1.setIco(appInfos.get(j).getIco());

                    }
                }
                applist.add(appInfo1);

            }
            initdata(applist);
        }



    }

    private void initdata(List<AppInfo> appInfos) {
        Infos.clear();
        for (int i = (pos - 1) * 6; i < (pos - 1) * 6 + 6; i++) {
            if (i < appInfos.size()) {
                Infos.add(appInfos.get(i));
            }
        }

        Log.e("dddddd",Infos.size()+"");
        if (update_flag) {
            List<AppList> list = getList(update_apps_list);
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < Infos.size(); j++) {
                    if (list.get(i).getAppName().equals(Infos.get(j).getName())) {
                        Infos.get(j).setUpdate_flag(true);
                    }
                }
            }
        }

        deskTopAdapter = new DeskTopAdapter(Infos, getContext());
        mGridView.setAdapter(deskTopAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        mGridView.setLayoutManager(gridLayoutManager);
        new ItemTouchHelper(mCallback).attachToRecyclerView(mGridView);



    }

    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT,0) {
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

            List<AppInfo> list = appInfoDao.queryBuilder().build().list();
            Log.e("aaaa",list.size()+"");
          /*  int a =(pos - 1) * 6+toPosition;
           int b =  (pos - 1) * 6+fromPosition;
            int type1 = appInfos.get((pos - 1) * 6 + toPosition).getType();*/
            list1.get((pos - 1) * 6+toPosition).setType((pos - 1) * 6+fromPosition);
          /*  int type = appInfos.get((pos - 1) * 6 + toPosition).getType();
            int type2 = appInfos.get((pos - 1) * 6 + fromPosition).getType();*/
            list1.get((pos - 1) * 6+fromPosition).setType((pos - 1) * 6+toPosition);
           // int type3 = appInfos.get((pos - 1) * 6 + fromPosition).getType();
            appInfoDao.updateInTx(list1);
            List<AppInfo> list1 = appInfoDao.queryBuilder().build().list();
            Log.e("aaaaaa",list1.size()+"");
            //deskTopAdapter.notifyDataSetChanged();
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
