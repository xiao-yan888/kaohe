package com.example.zhmkaohe.bean;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_infos")
public class AppInfo  {
    @Id(autoincrement = true)
    private Long id;
    @Transient
    public String versionName;
    @Transient
    private String packageName;
    @Transient//包名
    private Drawable ico;       //图标
    @Transient
    private Boolean update_flag=false;
    @Property
    private String Name;        //应用标签
    private int type;//启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity
    @Generated(hash = 162037369)
    public AppInfo(Long id, String Name, int type) {
        this.id = id;
        this.Name = Name;
        this.type = type;
    }
    @Generated(hash = 1656151854)
    public AppInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIco() {
        return ico;
    }

    public void setIco(Drawable ico) {
        this.ico = ico;
    }

    public Boolean getUpdate_flag() {
        return update_flag;
    }

    public void setUpdate_flag(Boolean update_flag) {
        this.update_flag = update_flag;
    }
    /*public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Boolean getUpdate_flag() {
        return update_flag;
    }

    public void setUpdate_flag(Boolean update_flag) {
        this.update_flag = update_flag;
    }
    @Transient
    private Boolean update_flag=false;

    @Generated(hash = 162037369)
    public AppInfo(Long id, String Name, int type) {
        this.id = id;
        this.Name = Name;
        this.type = type;
    }

    @Generated(hash = 1656151854)
    public AppInfo() {
    }






    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIco() {
        return ico;
    }

    public void setIco(Drawable ico) {
        this.ico = ico;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
*/
  

   /* public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }*/



}
