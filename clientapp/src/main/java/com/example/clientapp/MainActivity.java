package com.example.clientapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 更新
     */
    private Button mBtGeng;
    /**
     * 进入/退出
     */
    private Button mBtJin;
    /**
     * 签到
     */
    private Button mBtQian;
    /**
     * 数据库
     */
    private Button mBtGreen;
    /**
     * 应用列表
     */
    private Button mBtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
    }

    private void initView() {
        mBtGeng = (Button) findViewById(R.id.bt_geng);
        mBtGeng.setOnClickListener(this);
        mBtJin = (Button) findViewById(R.id.bt_jin);
        mBtJin.setOnClickListener(this);
        mBtQian = (Button) findViewById(R.id.bt_qian);
        mBtQian.setOnClickListener(this);
        mBtGreen = (Button) findViewById(R.id.bt_green);
        mBtGreen.setOnClickListener(this);
        mBtList = (Button) findViewById(R.id.bt_list);
        mBtList.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_geng:
                Intent intent = new Intent("cn.boc.mtms.CHECK_VERSION_COMPLETE");
                intent.putExtra("CHECK_RESULT", true);
                intent.putExtra("CHECK_FAIL_REASON", "");
                intent.putExtra("UPDATE_FLAG", true);
                intent.putExtra("UPDATE_APPS_LIST", "[{\"appName\":\"浏览器\",\"packName\":\"com.newland.payment\",\"version\":\"1.0.00\",\"forceUpdate\":false}]");
                intent.setComponent(new ComponentName("com.example.zhmkaohe", "com.example.zhmkaohe.app.MyReceiver"));
                sendBroadcast(intent);
                break;
            case R.id.bt_jin:
                Intent intent1 = new Intent("cn.boc.mtms.UPDATE_STATUS");
                intent1.putExtra("UPDATE_STATUS", true);
                intent1.putExtra("RESULT_CODE", "0");
                intent1.putExtra("UPDATE_APPS_LIST", "[{\"appName\":\"浏览器\",\"packName\":\"com.newland.payment\",\"version\":\"1.0.00\",\"forceUpdate\":false}]");
                intent1.setComponent(new ComponentName("com.example.zhmkaohe", "com.example.zhmkaohe.app.MyReceiver"));
                sendBroadcast(intent1);
                break;
            case R.id.bt_qian:
                Intent intent2 = new Intent("cn.boc.mtms.DEVICE_NO_SIGNIN");
                intent2.putExtra("equip", true);
                intent2.setComponent(new ComponentName("com.example.zhmkaohe", "com.example.zhmkaohe.app.MyReceiver"));
                sendBroadcast(intent2);
                break;
            case R.id.bt_green:
                Intent intent3 = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent3);
                break;
            case R.id.bt_list:
                Intent intent4 = new Intent(MainActivity.this,Main3Activity.class);
                startActivity(intent4);
                break;
        }
    }
}
