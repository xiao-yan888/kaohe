package com.example.zhmkaohe.bean;

import android.os.Parcel;
import android.os.Parcelable;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_first")
public class FirstInfo implements Parcelable {
    @Id(autoincrement = false)
    private Long id;
    @Property
    private String title;
    @Property
    private String content;
    @Generated(hash = 1880401179)
    public FirstInfo(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeString(title);
        parcel.writeLong(id);
    }

    public static final Parcelable.Creator<FirstInfo> CREATOR = new Creator<FirstInfo>()
    {
        @Override
        public FirstInfo[] newArray(int size)
        {
            return new FirstInfo[size];
        }

        @Override
        public FirstInfo createFromParcel(Parcel in)
        {
            return new FirstInfo(in);
        }
    };

    public FirstInfo(Parcel in)
    {
        content = in.readString();
        title = in.readString();
        id = in.readLong();
    }
}
