package com.footprint.footprint.music;

import android.app.Application;


public class Ting extends Application {

    private static Ting mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Ting getInstance(){
        return mContext;
    }
}
