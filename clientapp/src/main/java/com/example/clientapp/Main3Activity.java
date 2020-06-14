package com.example.clientapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.clientapp.adapter.DataAdapter;
import com.example.clientapp.adapter.ListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private RecyclerView mRvZhuo;
    private RecyclerView mRvQi;
    private List<String> list;
    private List<String> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        SharedPreferences sp = getSharedPreferences("share", Context.MODE_PRIVATE);
        String data = sp.getString("data", "");
        String data1 = sp.getString("data1", "");
        Gson gson = new Gson();
        list = gson.fromJson(data, new TypeToken<List<String>>() {
        }.getType());
        list1 = gson.fromJson(data1, new TypeToken<List<String>>() {
        }.getType());
        Log.e("aaaaaaa", list.size() + "");
        Log.e("aaaaaa", list1.size() + "");
        initView();
    }


    private void initView() {
        mRvZhuo = (RecyclerView) findViewById(R.id.rv_zhuo);
        mRvQi = (RecyclerView) findViewById(R.id.rv_qi);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        mRvQi.setLayoutManager(linearLayoutManager);
        mRvZhuo.setLayoutManager(linearLayoutManager1);
        ListAdapter dataAdapter = new ListAdapter(Main3Activity.this, list);
        ListAdapter dataAdapter1 = new ListAdapter(Main3Activity.this, list1);
        mRvZhuo.setAdapter(dataAdapter);
        mRvQi.setAdapter(dataAdapter1);
    }
}
