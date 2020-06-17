package com.example.zhmkaohe;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
    private FragmentAdapter fragmentAdapter;
    private  int i;
    private LinearLayout mLl;
    private  int fragshu;
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
        //发送安装 卸载应用广播
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED"); //安装
        filter.addAction("android.intent.action.PACKAGE_REMOVED"); //卸载
        filter.addDataScheme("package");
        this.registerReceiver(myReceiver, filter);

        initView();
        mLl = (LinearLayout) findViewById(R.id.ll);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //设置列表上边布局占总屏幕的1/3
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
        //判断设备是否签到
        Boolean equi = (Boolean) SharedPreferencesUtils.getParam(this, "equi", false);
        // String updatelist = (String) SharedPreferencesUtils.getParam(this, "updatelist", "");
        if (equi) {
            //true 显示设备按钮
            mTvEqu.setVisibility(View.VISIBLE);
        } else {
            //false 隐藏设备按钮
            mTvEqu.setVisibility(View.GONE);
        }
        //动态添加其他应用fragment页面
        List<AppInfo> appInfos = MyApp.GetAppList1(MainActivity.this);
        //一共显示多少fragment页面
        int size = appInfos.size();
        i = size / 6;
        if (i * 6 == size) {
            fragshu = i;
        } else {
            fragshu = i + 1;
        }
        //添加fragment显示
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
    //初始化
    private void initView() {
        //崩溃之后重启机制
        application = (MyApp) getApplication();
        application.init();
        application.addActivity(this);
        //Boolean clientlist = (Boolean) SharedPreferencesUtils.getParam(MainActivity.this, "clientlist", false);
        //发送数据库中数据到客户端显示
        sendapplist();

        mTvEqu = (TextView) findViewById(R.id.tv_equ);
        mTvEqu.setOnClickListener(this);
        mApply = (TextView) findViewById(R.id.apply);
        mApply.setOnClickListener(this);
        mTvBeng = (TextView) findViewById(R.id.tv_beng);
        mTvBeng.setOnClickListener(this);
        //是否进入/退出显示dialog
        showDialog();

    }
    //发送数据库中数据到客户端
    private void sendapplist() {
        DaoSession daoSession = MyApp.getDaoSession();
        FirstInfoDao firstInfoDao = daoSession.getFirstInfoDao();
        AppInfoDao appInfoDao = daoSession.getAppInfoDao();
        List<FirstInfo> list = firstInfoDao.queryBuilder().build().list();
        List<AppInfo> list1 = appInfoDao.queryBuilder().build().list();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> date1 = new ArrayList<>();
        if (list.size() != 0) {
            for (int j = 0; j < list.size(); j++) {
                date.add(list.get(j).getTitle() + "  " + list.get(j).getContent());
            }
        }
        if (list1.size() != 0) {
            for (int j = 0; j < list1.size(); j++) {
                date1.add(list1.get(j).getName());
            }
        }
        //发送广播传递数据
        Intent intent = new Intent("cn.boc.mtms.CHECK");
        intent.putStringArrayListExtra("list", date);
        intent.putStringArrayListExtra("list1", date1);
        intent.setComponent(new ComponentName("com.example.clientapp", "com.example.clientapp.app.MyReceiver"));
        sendBroadcast(intent);

    }
    //更新/进入Dialog
    private void showDialog() {
        Boolean update_status = (Boolean) SharedPreferencesUtils.getParam(this, "update_status", false);
        //是否进入更新应用
        if (update_status) {
            //进入
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("进入更新应用");
            builder.setCancelable(true);
            //设置正面按钮
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Toast.makeText(context, "你点击了是的", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            //设置反面按钮
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Toast.makeText(context, "你点击了不是", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            builder.show();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_equ:
                //点击进入设备页面
                Intent intent = new Intent(this, EquipActivity.class);
                startActivity(intent);
                break;
            case R.id.apply:
                //点击进入应用列表显示页面
                Intent intent1 = new Intent(this, ApplyActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_beng:
                //人为伪造的崩溃机制
                press();
                break;
        }
    }

    /**
     * 人为制造的异常
     */
    public void press() {
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
    //显示fragment adapter
    public class FragmentAdapter extends FragmentPagerAdapter {
        FragmentAdapter(FragmentManager fm) {
            super(fm);
            notifyDataSetChanged();
            //this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {

            Log.e("sdsadsd", i + "");
            if (position == 0) {
                //首页页面
                FirstFragment firstFragment = new FirstFragment();
                return firstFragment;
            } else {
                //其他应用页面
                SecondFragment secondFragment = new SecondFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                //bundle.putString("title", titles[position]);
                secondFragment.setArguments(bundle);
                return secondFragment;
            }
        }

        @Override
        public int getCount() {
            return fragshu + 1;
        }

    }

}
