package com.example.zhmkaohe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.zhmkaohe.adapter.ListAdapter;
import com.example.zhmkaohe.app.MyApp;
import com.example.zhmkaohe.bean.RequestData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//其他页面展示数据
public class ListActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private String urlpath = "http://v.juhe.cn/toutiao/index?type=top&key=668a12f99d99a1120f9f82ae9ab4c7e0";
    private MyApp application;
    /**
     * 展示数据
     */
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<RequestData.ResultBean.DataBean> data = (List<RequestData.ResultBean.DataBean>) msg.obj;
            ListAdapter adapter = new ListAdapter(ListActivity.this, data);
            mRv.setAdapter(adapter);
        }
    };

    private void requestNewData() {

        new Thread() {
            @Override
            public void run() {
                requestData();
            }
        }.start();

    }

    private void requestData() {

        try {
            URL url = new URL(urlpath);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                String json = toJson(inputStream);
                Gson gson = new Gson();
                RequestData requestData = gson.fromJson(json, RequestData.class);
                List<RequestData.ResultBean.DataBean> data = requestData.getResult().getData();
                Message message = Message.obtain();
                message.obj = data;
                handler.sendMessage(message);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mRv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        requestNewData();
        // requestData();
        mTv = (TextView) findViewById(R.id.tv);
    }


    private String toJson(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        String string;
        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(in);
        try {
            while ((string = reader.readLine()) != null) {
                builder.append(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }


}
