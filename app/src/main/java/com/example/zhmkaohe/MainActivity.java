package com.example.zhmkaohe;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.app.MyReceiver;
import com.example.zhmkaohe.bean.AppInfo;
import com.example.zhmkaohe.bean.FirstInfo;
import com.example.zhmkaohe.fragment.FirstFragment;
import com.example.zhmkaohe.fragment.SecondFragment;
import com.example.zhmkaohe.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.hnshangyu.testgreendao.greendao.AppInfoDao;
import cn.hnshangyu.testgreendao.greendao.DaoSession;
import cn.hnshangyu.testgreendao.greendao.FirstInfoDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.vpager)
    ViewPager mVpager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private int i;
    private LinearLayout mLl;
    private int fragshu;
    /**
     * 设备
     */
    private TextView mTvEqu;
    private MyReceiver myReceiver;
    /**
     * 刷卡
     */
    private TextView mApply;
    private MyApp application;
    /**
     * 扫一扫
     */
    private TextView mTvBeng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /*   Intent intent1 = new Intent("android.intent.action.PACKAGE_ADDED");
        sendBroadcast(intent1);
        Intent intent = new Intent("android.intent.action.PACKAGE_REMOVED");
        sendBroadcast(intent);*/
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED"); //安装
        filter.addAction("android.intent.action.PACKAGE_REMOVED"); //卸载
        filter.addDataScheme("package");
        this.registerReceiver(myReceiver, filter);

        initView();
        mLl = (LinearLayout) findViewById(R.id.ll);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int heightPixels = metrics.heightPixels;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLl.getLayoutParams();
        layoutParams.height = heightPixels / 3;
        mLl.setLayoutParams(layoutParams);

        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initview();
    }

    private void initview() {
        Boolean equi = (Boolean) SharedPreferencesUtils.getParam(this, "equi", false);
        // String updatelist = (String) SharedPreferencesUtils.getParam(this, "updatelist", "");
        if (equi) {
            mTvEqu.setVisibility(View.VISIBLE);
        } else {
            mTvEqu.setVisibility(View.GONE);
        }
        List<AppInfo> appInfos = MyApp.GetAppList1(MainActivity.this);
        int size = appInfos.size();
        i = size / 6;
        if (i * 6 == size) {
            fragshu = i;
        } else {
            fragshu = i + 1;
        }
        fragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVpager.setAdapter(fragmentAdapter);
                mVpager.setCurrentItem(0);
                fragmentAdapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        fragmentAdapter.notifyDataSetChanged();
    }

    private void initView() {
       /* application = (MyApp) getApplication();
        application.init();
        application.addActivity(this);*/
        DaoSession daoSession = MyApp.getDaoSession();
        FirstInfoDao firstInfoDao = daoSession.getFirstInfoDao();
        AppInfoDao appInfoDao = daoSession.getAppInfoDao();
        List<FirstInfo> list = firstInfoDao.queryBuilder().build().list();
        List<AppInfo> list1 = appInfoDao.queryBuilder().build().list();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> date1 = new ArrayList<>();
        if (list.size()!=0) {
            for (int j = 0; j < list.size(); j++) {
                date.add(list.get(j).getTitle() + "  " + list.get(j).getContent());
            }
        }
        if (list1.size()!=0) {
            for (int j = 0; j < list1.size(); j++) {
                date1.add(list1.get(j).getName());
            }
        }
        Intent intent = new Intent("cn.boc.mtms.CHECK");
        intent.putStringArrayListExtra("list", date);
        intent.putStringArrayListExtra("list1", date1);
        intent.setComponent(new ComponentName("com.example.clientapp", "com.example.clientapp.app.MyReceiver"));
        sendBroadcast(intent);
        mTvEqu = (TextView) findViewById(R.id.tv_equ);
        mTvEqu.setOnClickListener(this);
        mApply = (TextView) findViewById(R.id.apply);
        mApply.setOnClickListener(this);
        mTvBeng = (TextView) findViewById(R.id.tv_beng);
        mTvBeng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_equ:
                Intent intent = new Intent(this, EquipActivity.class);
                startActivity(intent);
                break;
            case R.id.apply:
                Intent intent1 = new Intent(this, ApplyActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_beng:
                press();
                break;
        }
    }
    /**
     * 人为制造的异常*/
    public void press(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mApply.setText("dfsd");
            }
        }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;


        FragmentAdapter(FragmentManager fm) {
            super(fm);
            notifyDataSetChanged();
            //this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {

            Log.e("sdsadsd", i + "");
            if (position == 0) {
                FirstFragment firstFragment = new FirstFragment();
                return firstFragment;
            } else {
                SecondFragment secondFragment = new SecondFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                //bundle.putString("title", titles[position]);
                secondFragment.setArguments(bundle);
                return secondFragment;
            }
           /* Fragment fragment = mFragmentList.get(position);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);

            fragment.setArguments(bundle);
            return fragment;*/
        }

        @Override
        public int getCount() {
            return fragshu + 1;
        }

      /*  @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }*/
    }

}
