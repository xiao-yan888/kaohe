package com.example.zhmkaohe;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.clientapp.IMyAidlInterface;
import com.example.zhmkaohe.adapter.EquipAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.internal.Utils;

public class EquipActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private List<String> list = new ArrayList<>();
    private IMyAidlInterface iMyAidlInterface;
    private List<String> infos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip);
        ActionBar actionBar = getSupportActionBar();   //隐藏原本的标题栏
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                EquipAdapter equipAdapter = new EquipAdapter(EquipActivity.this, infos, list);
                mRv.setAdapter(equipAdapter);
            }
        }
    };
    private void initView() {
        list.add("uscc");
        list.add("终端号");
        list.add("商户号");
        list.add("日期");
        list.add("时间");
        list.add("空间剩余容量");
        list.add("空间总容量");
        list.add("当前网络状态");
        mRv = (RecyclerView) findViewById(R.id.rv);
        //new EquipAdapter(this, date);
      //  mRv.setAdapter(firstAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRv.setLayoutManager(linearLayoutManager);
        Intent intent=new Intent();
        intent.setAction("com.example.clientapp.MyService");
        intent.setPackage("com.example.clientapp");
        boolean b = getApplicationContext().bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //等待取得mService
                    if(null != iMyAidlInterface){
                        //...
                        try {
                            infos = iMyAidlInterface.getInfos();
                            Log.e("ssssssss", infos.size()+"");
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);


                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    //...

                }
            }

        }).start();

        Log.e("aaa",b+"");
    }

  private ServiceConnection serviceConnection=  new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("xxxx","aaaa");
        }
    };
}
