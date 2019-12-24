package com.footprint.footprint.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 功能：Activity管理工具类
 */
public class ActivityManagerUtils {

	private static List<Activity> activitys = new ArrayList<Activity>();

	private static List<Activity> clearActivity = new ArrayList<Activity>();

	/**
	 * activity add
	 *
	 * @param paramActivity
	 */
	public static void add(Activity paramActivity) {
		synchronized (activitys) {
			activitys.add(paramActivity);
			return;
		}
	}


	/**
	 * activity add
	 *
	 * @param paramActivity
	 */
	public static void addOneActivity(Activity paramActivity) {
		synchronized (clearActivity) {
			clearActivity.add(paramActivity);
			return;
		}
	}

	/**
	 * activity remove
	 *
	 * @param paramActivity
	 */
	public static void remove(Activity paramActivity) {
		synchronized (clearActivity) {
			if (clearActivity.contains(paramActivity)) {
				clearActivity.remove(paramActivity);
			}
		}
	}/**
	 * activity remove
	 *
	 * @param paramActivity
	 */
	public static void removeOne(Activity paramActivity) {
		synchronized (activitys) {
			if (activitys.contains(paramActivity)) {
				activitys.remove(paramActivity);
			}
		}
	}
	/**
	 * activity remove
	 *
	 *
	 */
	public static void clear() {
		synchronized (clearActivity) {
			Iterator<Activity> iterator = clearActivity.iterator();
			while(iterator.hasNext()){
				((Activity) iterator.next()).finish();
			}
			clearActivity.clear();

		}
	}
	/**
	 * exit app
	 */
	public static void stopAll() {
		synchronized (activitys) {
			Iterator<Activity> iterator = activitys.iterator();
//			if (!iterator.hasNext()) {
//				activitys.clear();
//				return;
//			}
			while(iterator.hasNext()){
				((Activity) iterator.next()).finish();
			}
			activitys.clear();
//			((Activity) iterator.next()).finish();
//			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

}
