package com.footprint.footprint.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UserInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String pwd;
    private String userName;
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
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
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
    @Generated(hash = 580600770)
    public UserInfo(Long id, String name, String pwd, String userName,
            String beizhu1, String beizhu2, String beizhu3) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.userName = userName;
        this.beizhu1 = beizhu1;
        this.beizhu2 = beizhu2;
        this.beizhu3 = beizhu3;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
}
