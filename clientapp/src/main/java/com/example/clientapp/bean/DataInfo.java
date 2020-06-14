package com.example.clientapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_data")
public class DataInfo {
    @Id
    private Long id;
    @Property
    private String name;
    private String age;
    private String sex;
    private String content;
    @Generated(hash = 2081879932)
    public DataInfo(Long id, String name, String age, String sex, String content) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.content = content;
    }

    public DataInfo(String name) {
        this.name = name;
    }

    @Generated(hash = 1853228192)
    public DataInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
