package com.example.clientapp.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ArrayList<String> list = intent.getStringArrayListExtra("list");
        ArrayList<String> list1 = intent.getStringArrayListExtra("list1");
        Log.e("aaa",list.size()+"");
        Log.e("aaa",list1.size()+"");
        SharedPreferences sp = context.getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String data = gson.toJson(list);
        String data1 = gson.toJson(list1);
        editor.putString("data",data);
        editor.putString("data1",data1);
        editor.commit();

    }
}
