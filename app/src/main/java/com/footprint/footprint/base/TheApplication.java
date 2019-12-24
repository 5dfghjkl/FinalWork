package com.footprint.footprint.base;

import android.app.Application;
import android.app.Service;
import android.database.sqlite.SQLiteDatabase;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.footprint.footprint.db.green.DaoMaster;
import com.footprint.footprint.db.green.DaoSession;
import com.footprint.footprint.db.green.UserInfoDao;
import com.footprint.footprint.dbbean.UserInfo;
import com.footprint.footprint.utils.LocationService;

import java.util.List;


/**
 * 功能:程序的全局数据
 */
public class TheApplication extends Application {
  private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static TheApplication instance = null;
    public LocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        instance = this;
        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());
       setDatabase();
        UserInfoDao userInfoDao = mDaoSession.getUserInfoDao();
        List<UserInfo> userInfoList=userInfoDao.loadAll();
        if(userInfoList==null||userInfoList.size()<1){
            UserInfo userInfo=new UserInfo();
            userInfo.setName("admin");
            userInfo.setPwd("123456");
            userInfoDao.insert(userInfo);
            UserInfo userInfo1=new UserInfo();
            userInfo1.setName("admin1");
            userInfo1.setPwd("123456");
            userInfoDao.insert(userInfo1);
            UserInfo userInfo2=new UserInfo();
            userInfo2.setName("admin2");
            userInfo2.setPwd("123456");
            userInfoDao.insert(userInfo2);
        }
    }

    private void setDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
