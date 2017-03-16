package com.common.lhk.library.crash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.common.lhk.library.files.AppDataFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 处理程序中未捕获的异常，将异常写入日志文件
 */
public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = CrashHandler.class.getSimpleName();

	private static CrashHandler instance = null;

	private Context mContext;

	/**
	 * 系统默认的UncaughtException处理类
	 */
	private UncaughtExceptionHandler mDefaultHandler;

	/**
	 * 用来存储设备信息和异常信息
	 */
	private Map<String, String> infos = new HashMap<String, String>();

	/**
	 * 用于格式化日期,作为日志文件名的一部分
	 */
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());

	private CrashHandler() {
	}

	/**
	 * 保证只有一个CrashHandler实例
	 */
	private CrashHandler(Context context) {
		mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 获取CrashHandler实例 ,单例模式
	 */
	public static CrashHandler getInstance(Context context) {
		if (instance == null) {
			synchronized (CrashHandler.class) {
				if (instance == null) {
					instance = new CrashHandler(context);
				}
			}
		}
		return instance;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Log.e("error : ", e.getMessage());
			}
			System.exit(0);
		}
	}

	private boolean handleException(final Throwable ex) {

		// 如果是调试状态则不生成异常文件，让系统默认的异常处理器来处理
		if (Debug.isDebuggerConnected())
			return false;
		if (ex == null)
			return false;
		// 收集设备参数信息
		collectDeviceInfo(mContext);
		// 保存日志文件
		saveCrashInfo2File(ex);
		return true;
	}

	private void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				//应用的版本名称和版本号  
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
				
				//android版本号  
				infos.put("OS Version: ",String.valueOf(Build.VERSION.RELEASE));
				infos.put("SDK Version: ",String.valueOf(Build.VERSION.SDK_INT));
				
				//手机制造商  
				infos.put("Vendor: ",String.valueOf(Build.MANUFACTURER));

				//手机型号  
				infos.put("Model: ",String.valueOf(Build.MODEL));
       
				//cpu架构  
				infos.put("CPU ABI: ",String.valueOf(Build.CPU_ABI));//consider os version
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occurred when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
				Log.e(TAG, "an error occurred when collect crash info", e);
			}
		}
	}

	private void saveCrashInfo2File(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + "=" + value + "\n");
		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			String fileName = String.format("crash-%s.log", format.format(new Date(System.currentTimeMillis())));
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				String path = AppDataFile.getCrashLogFile().getAbsolutePath() + File.separator;
				FileOutputStream fos = new FileOutputStream(path + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
	}
}
