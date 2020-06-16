package com.example.zhmkaohe.bean;

import android.os.Parcel;
import android.os.Parcelable;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_first")
public class FirstInfo {
    @Id(autoincrement = false)
    private Long id;
    @Property
    private String title;
    @Property
    private String content;
    private int type;

    @Generated(hash = 1872619273)
    public FirstInfo(Long id, String title, String content, int type) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    @Generated(hash = 1937394943)
    public FirstInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
