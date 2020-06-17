package com.example.zhmkaohe;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.zhmkaohe.adapter.ApplyAdapter;
import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

//展示设备安装应用列表
public class ApplyActivity extends AppCompatActivity {

    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        initView();
        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initview();
    }

    private void initview() {
        //展示数据列表
        List<AppInfo> appInfos = MyApp.GetAppList1(ApplyActivity.this);
        Log.e("wwww", appInfos.size() + "");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        ApplyAdapter applyAdapter = new ApplyAdapter(this, appInfos);
        mRv.setAdapter(applyAdapter);

    }


    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
    }
}
