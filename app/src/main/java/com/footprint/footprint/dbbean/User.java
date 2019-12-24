package com.footprint.footprint.dbbean;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by My on 2017/10/16.
 * 用户信息
 */

public class User {
    /**
     * sessionTerminate : false
     * result : true
     * session : 025477474
     * user : {"accountName":"超级管理员","accountPhone":"admin","addr":"郑州市未来路"}
     */
// 功能:获得用户的信息
    private static String getUserInfo(String key, Context context,
                                      String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }
    // 功能:设置用户信息
    private static void setUserInfo(String Key, String Value, Context context) {
        SharedPreferences settings = context.getSharedPreferences("UserInfo",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Key, Value);
        editor.commit();
    }
    public boolean sessionTerminate;
    public boolean result;
    public String session;
    public String reason;
    public UserBean data;

    public static class UserBean {
        /**
         * accountName : 超级管理员
         * accountPhone : admin
         * addr : 郑州市未来路
         */
        public String uNickname;
        public String uPhone;
        public String uCode;
        public String uPhoto;
        public String uSession;
        public String uSchoolIds;

    }

    /** 功能：获取当前用户的session */
    public static String getUserSession(Context context) {
        return getUserInfo("session", context, "");
    }

    /** 功能：设置当前用户的session */
    public static void setUserSession(String session, Context context) {
        setUserInfo("session", session, context);
    }
    /** 功能：获取当前用户的电话 */
    public static String getUserPhone(Context context) {
        return getUserInfo("phone", context, "");
    }

    /** 功能：设置当前用户的电话*/
    public static void setUserPhone(String phone, Context context) {
        setUserInfo("phone", phone, context);
    }
    /** 功能：获取当前用户的Id */
    public static String getUserId(Context context) {
        return getUserInfo("userId", context, "");
    }

    /** 功能：设置当前用户的Id*/
    public static void setUserId(String userId, Context context) {
        setUserInfo("userId", userId, context);
    }
    /** 功能：获取当前用户的地址 */
    public static String getUseradress(Context context) {
        return getUserInfo("adress", context, "");
    }

    /** 功能：设置当前用户的地址*/
    public static void setUseradress(String adress, Context context) {
        setUserInfo("adress", adress, context);
    }
    /** 功能：获取当前用户的地址 */
    public static String getSchoolId(Context context) {
        return getUserInfo("schoolid", context, "1");
    }

    /** 功能：设置当前用户的地址*/
    public static void setSchoolId(String schoolid, Context context) {
        setUserInfo("schoolid", schoolid, context);
    }
    /** 功能：获取当前用户的地址 */
    public static String getUserName(Context context) {
        return getUserInfo("username", context, "");
    }

    /** 功能：设置当前用户的地址*/
    public static void setUserName(String username, Context context) {
        setUserInfo("username", username, context);
    }
    /** 功能：获取当前用户的工号 */
    public static String getWorkerNo(Context context) {
        return getUserInfo("workerNo", context, "");
    }

    /** 功能：设置当前用户的工号*/
    public static void setWorkerNo(String workerNo, Context context) {
        setUserInfo("workerNo", workerNo, context);
    }
    /** 功能：获取当前用户的图片地址 */
    public static String getUserImage(Context context) {
        return getUserInfo("image", context, "");
    }

    /** 功能：设置当前用户的图片地址*/
    public static void setUserImage(String image, Context context) {
        setUserInfo("image", image, context);
    }
    /** 功能：获取当前用户的用户积分 */
    public static String getUserIntegral(Context context) {
        return getUserInfo("integral", context, "");
    }

    /** 功能：设置当前用户的用户积分*/
    public static void setUserIntegral(String integral, Context context) {
        setUserInfo("integral", integral, context);
    }
}
