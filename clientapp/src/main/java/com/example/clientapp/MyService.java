package com.example.clientapp;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyService extends Service {
    private String info = "";
    private List<String> infos=new ArrayList<>();

   @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    private Binder mBinder = new IMyAidlInterface.Stub() {

        @Override
        public List<String> getInfos() throws RemoteException {
            return infos;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        infos.add("succ");
        infos.add("123");
        infos.add("34435");
        infos.add(getdate(0));
        infos.add(getdate(1));
        infos.add(getAvailMemory());
        //infos.add(getTotalMemory());
        infos.add("64G");
        int apnType = getAPNType(this);
        //获取当前的网络状态 ：没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
        if (apnType==0){
           infos.add("没有网络");
        }else if (apnType==1){
            infos.add("WIFI");
        }else if (apnType==4){
            infos.add("4G网络");
        }else if (apnType==3){
            infos.add("3G网络");
        }else if (apnType==2){
            infos.add("2G网络");
        }

    }
    public static int getAPNType(Context context) {
        //结果返回值
        int netType = 0;
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = 1;
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 3;
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                netType = 2;
            } else {
                netType = 2;
            }
        }
        return netType;
    }

    private String getAvailMemory(){// 获取android当前可用内存大小  
        ActivityManager am=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi=new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);//mi.availMem; 当前系统的可用内存  
        return Formatter.formatFileSize(getBaseContext(),mi.availMem);// 将获取的内存大小规格化  
    }

    private String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件  
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小  
            arrayOfString = str2.split("//s+");
            for(String num : arrayOfString) {
                Log.i(str2, num + "/t");
            }
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte  
            localBufferedReader.close();
        } catch (IOException e) {

        }
        return Formatter.formatFileSize(getBaseContext(), initial_memory);// Byte转换为KB或者MB，内存大小规格化  
    }


    public String getdate(int time){
        String date = "";
        Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
        int year = calendar.get(Calendar.YEAR);
//月
        int month = calendar.get(Calendar.MONTH)+1;
//日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
        int minute = calendar.get(Calendar.MINUTE);
//秒
        int second = calendar.get(Calendar.SECOND);
        if (time==0){
            date = year+"年"+month+"月"+day;
            return date;
        }
        date = hour+":"+minute+":"+second;
        return date;
    }
}
