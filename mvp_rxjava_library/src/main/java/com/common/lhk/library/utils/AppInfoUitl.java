package com.common.lhk.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * APP信息工具类
 * @author root
 *
 */
public class AppInfoUitl {

	public static int getVersionCode(Context context) {
		try {
			PackageInfo packageInfo =getPackageInfo(context);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static String getVersionName(Context context) {
		try {
			PackageInfo packageInfo =getPackageInfo(context);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPackageName(Context context) {		
		return context.getApplicationInfo().packageName;		
	}

	public static PackageInfo getPackageInfo(Context context) throws NameNotFoundException{
		PackageInfo packageInfo =context.getPackageManager()
				.getPackageInfo(getPackageName(context),
						PackageManager.GET_ACTIVITIES);
		return packageInfo;
	}
}
