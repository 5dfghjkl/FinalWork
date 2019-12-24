package com.footprint.footprint.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

/**
 * 功能:获取屏幕尺寸，保存为偏好设置文件
 */
public class ScreenSizeUtils {

	private static SharedPreferences getPreferences(Context ctx) {
		return ctx.getSharedPreferences("Screen", 0);
	}

	public static void init(Activity ctx) {
		DisplayMetrics metrics = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		saveScreen(ctx, metrics.widthPixels, metrics.heightPixels);
	}

	public static void saveScreen(Context ctx, int width, int hight) {
		getPreferences(ctx).edit().putInt("width", width)
				.putInt("hight", hight).commit();
	}

	public static void saveHomeGridView(Context ctx, int width, int hight){
		getPreferences(ctx).edit().putInt("gridViewWidth", width)
				.putInt("gridViewHeight", hight).commit();
	}

	public static int getHomeGridWidth(Context ctx){
		return getPreferences(ctx).getInt("gridViewWidth", 0);
	}
	public static int getHomeGridHeight(Context ctx){
		return getPreferences(ctx).getInt("gridViewHeight", 0);
	}

	public static int getWidth(Context ctx) {
		return getPreferences(ctx).getInt("width", 0);
	}

	public static int getHight(Context ctx) {
		return getPreferences(ctx).getInt("hight", 0);
	}
}
