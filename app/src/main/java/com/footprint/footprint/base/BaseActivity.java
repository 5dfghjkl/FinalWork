package com.footprint.footprint.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.footprint.footprint.utils.ActivityManagerUtils;
import com.footprint.footprint.utils.ScreenSizeUtils;
import com.footprint.footprint.utils.ToastUtils;


/**
 * Created by My on 2017/10/10.
 */

public class BaseActivity extends AppCompatActivity {
    public static final String TAG = BaseActivity.class.getSimpleName();


    // 第一次按下返回键的时间
    private static long m_FirstTime;


    /**
     * 判断本地网络是否连接
     *
     * @param paramContext
     * @return
     */
    public static boolean isConnect(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) TheApplication.instance
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (localConnectivityManager != null) {
            NetworkInfo localNetworkInfo = localConnectivityManager
                    .getActiveNetworkInfo();
            if ((localNetworkInfo != null)
                    && (localNetworkInfo.isConnected())
                    && (localNetworkInfo.getState() == NetworkInfo.State.CONNECTED))
                return true;
        }
        return false;
    }

    public void connectShowMsg(Context ctx) {
        if (!isConnect(ctx)) {
            showPopwindow();
        }
    }
    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        ToastUtils.showToast(getBaseContext(),"联网失败，请保持网络畅通");
    }


    public void createToast(final String paramString) {
        ToastUtils.showToast(getBaseContext(),paramString);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ActivityManagerUtils.add(this);
        // 隐藏键盘
        getWindow().setSoftInputMode(3);
        // 保存屏幕尺寸
        ScreenSizeUtils.init(this);
        connectShowMsg(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManagerUtils.remove(this);
        ActivityManagerUtils.addOneActivity(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if ((event.getAction() == 0) && (getCurrentFocus() != null)
                && (getCurrentFocus().getWindowToken() != null))
            return ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), 0);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (!pressBackExitApp()) {
            return super.onKeyDown(keyCode, event);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - m_FirstTime > 2000) {
                createToast("再按一次退出程序");
                m_FirstTime = secondTime;
                return true;
            } else {
                ActivityManagerUtils.stopAll();
            }
        }
        return false;
    }

    /**
     * 功能：点击返回键退出app
     *
     * @return
     */
    protected boolean pressBackExitApp() {
        return false;
    }
    protected void setAppCompatText(int id,String text){
        AppCompatTextView textView=(AppCompatTextView)findViewById(id);
        textView.setText(text);
    }

}
