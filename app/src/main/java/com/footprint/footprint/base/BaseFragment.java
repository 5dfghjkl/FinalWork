package com.footprint.footprint.base;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.footprint.footprint.utils.ToastUtils;

/**
 * Created by My on 2017/10/10.
 */

public class BaseFragment extends Fragment {


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
        ToastUtils.showToast(getActivity().getBaseContext(),"联网失败，请保持网络畅通");
    }


    public void createToast(final String paramString) {
        ToastUtils.showToast(getActivity().getBaseContext(),paramString);
    }


    public AppCompatTextView getAppCompatTextView(View view, int id){
        return (AppCompatTextView)view.findViewById(id);
    }
protected Activity mactivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
   this.mactivity=activity;
    }

}
