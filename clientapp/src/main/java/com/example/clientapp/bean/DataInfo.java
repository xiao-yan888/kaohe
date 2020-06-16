package com.example.clientapp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "t_data")
public class DataInfo {
    @Id(autoincrement = true)
    private Long id;
    @Property
    private String name;
    private String age;
    private String sex;
    private String content;
    private String fuo;
    private String fut;
    private String fus;
    private String fuf;
    private String fufi;
    @Generated(hash = 848058640)
    public DataInfo(Long id, String name, String age, String sex, String content,
            String fuo, String fut, String fus, String fuf, String fufi) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.content = content;
        this.fuo = fuo;
        this.fut = fut;
        this.fus = fus;
        this.fuf = fuf;
        this.fufi = fufi;
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

    public String getFuo() {
        return this.fuo;
    }

    public void setFuo(String fuo) {
        this.fuo = fuo;
    }

    public String getFut() {
        return this.fut;
    }

    public void setFut(String fut) {
        this.fut = fut;
    }

    public String getFus() {
        return this.fus;
    }

    public void setFus(String fus) {
        this.fus = fus;
    }

    public String getFuf() {
        return this.fuf;
    }

    public void setFuf(String fuf) {
        this.fuf = fuf;
    }

    public String getFufi() {
        return this.fufi;
    }

    public void setFufi(String fufi) {
        this.fufi = fufi;
    }

}
