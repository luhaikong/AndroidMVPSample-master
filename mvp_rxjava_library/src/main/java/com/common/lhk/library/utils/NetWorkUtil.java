package com.common.lhk.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * 网络状态工具类
 */
public class NetWorkUtil {

	/**
	 * 获取当前的网络状类型
	 * @param context
	 * @return  
	 * 	-1：没有网络,
	 *   1：WIFI网络,
	 *   2：wap网络,
	 *   3：net网络,
	 *   4: 有线网络
	 */
	public static int getNetworkType(Context context) {
		int netType = -1;
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo == null) {
			return netType;
		}
		
		int nType = networkInfo.getType();
		switch(nType){
		case ConnectivityManager.TYPE_MOBILE:
			if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
				netType = 3;
			} else {
				netType = 2;
			}
			break;
		case ConnectivityManager.TYPE_WIFI:
			netType = 1;
			break;
		case ConnectivityManager.TYPE_ETHERNET:
			netType = 4;
			break;
		}
		return netType;
	}
	
	/**
	 * 获取网络连接的状态
	 * @param context
	 * @return true 有网络连接。
	 */
	public static boolean getNetworkStatus(Context context){
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo==null){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断WiFi网络是否可用
	 * 
	 * @param context
	 * @return true 可用
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断手机网络是否可用
	 * 
	 * @param context
	 * @return true 可用
	 */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断有线网络是否可用
	 * 
	 * @param context
	 * @return true可用
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mEthNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
			if (mEthNetworkInfo != null) {
				return mEthNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 获取本机IP地址
	 */
    public static String getEthIP() {
        String ip = "";
        try {
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) e1.nextElement();
                if (!networkInterface.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = networkInterface.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress inetAddress = (InetAddress) e2.nextElement();
                        if (inetAddress instanceof Inet6Address)
                            continue;
                        ip = inetAddress.getHostAddress();
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            //System.exit(-1);
        }
        return ip;
    }
}
