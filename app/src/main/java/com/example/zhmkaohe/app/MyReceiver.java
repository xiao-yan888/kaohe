package com.example.zhmkaohe.app;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.zhmkaohe.EquipActivity;
import com.example.zhmkaohe.util.SharedPreferencesUtils;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收更新广播
        if(intent.getAction().equals("cn.boc.mtms.CHECK_VERSION_COMPLETE")){
            boolean check_result = intent.getBooleanExtra("CHECK_RESULT", false);
            //是否更新
            boolean update_flag = intent.getBooleanExtra("UPDATE_FLAG", false);
            //要更新的列表
            String update_apps_list = intent.getStringExtra("UPDATE_APPS_LIST");

            if (update_flag) {
                //true 保存数据
                SharedPreferencesUtils.setParam(context,"update_flag",update_flag);
                SharedPreferencesUtils.setParam(context,"update_apps_list",update_apps_list);
                Log.i("MyTag", "onReceive: 获取到YangLiWei!");
                Log.i("MyTag", update_apps_list);
            }else {
                Log.i("MyTag", "onReceive: 没有获取到");
            }
            //接收进入/退出更新广播
        }else if (intent.getAction().equals("cn.boc.mtms.UPDATE_STATUS")){
            //是否进入 true 进入 false 退出
            boolean update_status = intent.getBooleanExtra("UPDATE_STATUS", true);
            String update_apps_list = intent.getStringExtra("UPDATE_APPS_LIST");
            /*SharedPreferencesUtils.setParam(context,"update_status",update_status);
            if (update_apps_list!=null){
                SharedPreferencesUtils.setParam(context,"updatelist",update_apps_list);
            }*/
            if (update_status) {
                SharedPreferencesUtils.setParam(context,"update_status",update_status);

            }

            Log.i("MyTag", update_apps_list);
            //设备是否签到
        }else if (intent.getAction().equals("cn.boc.mtms.DEVICE_NO_SIGNIN")){
            Log.i("MyTag", "设备未签到");
            //是否签到，true签到 false 未签到
            boolean equip = intent.getBooleanExtra("equip", true);
            SharedPreferencesUtils.setParam(context,"equi",equip);

        }else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
            //监听安装应用广播 获取安装包名
            String packageName = intent.getData().getSchemeSpecificPart();
            SharedPreferencesUtils.clear(context);
            SharedPreferencesUtils.setParam(context,"addname",packageName);
            Log.i("MyTag", "已安装新应用"+packageName);
            Toast.makeText(context,"已安装新应用"+packageName,Toast.LENGTH_LONG);

        }else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){
            //监听卸载应用包名
            String packageName = intent.getData().getSchemeSpecificPart();
            SharedPreferencesUtils.setParam(context,"rename",packageName);
            Log.i("MyTag", "卸载应用"+packageName);
            Toast.makeText(context,"卸载应用"+packageName,Toast.LENGTH_LONG);

        }

    }




}
