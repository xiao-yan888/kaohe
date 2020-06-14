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
        if(intent.getAction().equals("cn.boc.mtms.CHECK_VERSION_COMPLETE")){
            boolean check_result = intent.getBooleanExtra("CHECK_RESULT", false);
            boolean update_flag = intent.getBooleanExtra("UPDATE_FLAG", false);
            String update_apps_list = intent.getStringExtra("UPDATE_APPS_LIST");
            if (update_flag) {
                SharedPreferencesUtils.setParam(context,"update_flag",update_flag);
                SharedPreferencesUtils.setParam(context,"update_apps_list",update_apps_list);
                Log.i("MyTag", "onReceive: 获取到YangLiWei!");
                Log.i("MyTag", update_apps_list);
            }else {
                Log.i("MyTag", "onReceive: 没有获取到");
            }
        }else if (intent.getAction().equals("cn.boc.mtms.UPDATE_STATUS")){
            boolean update_status = intent.getBooleanExtra("UPDATE_STATUS", true);
            String update_apps_list = intent.getStringExtra("UPDATE_APPS_LIST");
            /*SharedPreferencesUtils.setParam(context,"update_status",update_status);
            if (update_apps_list!=null){
                SharedPreferencesUtils.setParam(context,"updatelist",update_apps_list);
            }*/
            if (update_status) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
            }

            Log.i("MyTag", update_apps_list);
        }else if (intent.getAction().equals("cn.boc.mtms.DEVICE_NO_SIGNIN")){
            Log.i("MyTag", "设备未签到");
            boolean equip = intent.getBooleanExtra("equip", true);
            SharedPreferencesUtils.setParam(context,"equi",equip);

        }else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
            Log.i("MyTag", "已安装新应用");
            Toast.makeText(context,"已安装新应用",Toast.LENGTH_LONG);

        }else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){
            Log.i("MyTag", "卸载应用");
            Toast.makeText(context,"卸载应用",Toast.LENGTH_LONG);

        }

    }




}
