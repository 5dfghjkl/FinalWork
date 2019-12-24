package com.footprint.footprint.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class locationData {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private double jd;
    private double wd;
    private String time;
    private String beizhu1;
    private String beizhu2;
    private String beizhu3;
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
    public double getWd() {
        return this.wd;
    }
    public void setWd(double wd) {
        this.wd = wd;
    }
    public double getJd() {
        return this.jd;
    }
    public void setJd(double jd) {
        this.jd = jd;
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
    @Generated(hash = 183546466)
    public locationData(Long id, String name, double jd, double wd, String time,
            String beizhu1, String beizhu2, String beizhu3) {
        this.id = id;
        this.name = name;
        this.jd = jd;
        this.wd = wd;
        this.time = time;
        this.beizhu1 = beizhu1;
        this.beizhu2 = beizhu2;
        this.beizhu3 = beizhu3;
    }
    @Generated(hash = 1157866364)
    public locationData() {
    }
   
}
