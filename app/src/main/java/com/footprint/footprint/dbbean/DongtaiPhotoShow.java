package com.footprint.footprint.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DongtaiPhotoShow {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String title;
    private String code;
    private String time;
    private String beizhu1;
    private String beizhu2;
    private String beizhu3;
    private String beizhu4;
    public String getBeizhu4() {
        return this.beizhu4;
    }
    public void setBeizhu4(String beizhu4) {
        this.beizhu4 = beizhu4;
    }
    public String getBeizhu3() {
        return this.beizhu3;
    }
    public void setBeizhu3(String beizhu3) {
        this.beizhu3 = beizhu3;
    }
    public String getBeizhu2() {
        return this.beizhu2;
    }
    public void setBeizhu2(String beizhu2) {
        this.beizhu2 = beizhu2;
    }
    public String getBeizhu1() {
        return this.beizhu1;
    }
    public void setBeizhu1(String beizhu1) {
        this.beizhu1 = beizhu1;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1461402772)
    public DongtaiPhotoShow(Long id, String name, String title, String code,
            String time, String beizhu1, String beizhu2, String beizhu3,
            String beizhu4) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.code = code;
        this.time = time;
        this.beizhu1 = beizhu1;
        this.beizhu2 = beizhu2;
        this.beizhu3 = beizhu3;
        this.beizhu4 = beizhu4;
    }
    @Generated(hash = 1126233942)
    public DongtaiPhotoShow() {
    }
}
