package com.common.lhk.library.files;

import android.os.Environment;

import java.io.File;

/**
 * 使用方法：
 * 1、在Application类或其子类中调用进行初始化,AppDataFile.init(“根路径”);
 * 2、使用时直接File file = AppDataFile.getCacheFile();
 */
public class AppDataFile {

    private File sdcard = Environment.getExternalStorageDirectory();
    private File mAppFile;
	private static AppDataFile mAppDataFile;
	private static final String FILE_ROOT_PATH="luhaikong";

	/**
	 * 缓存
	 */
	private static final String FILE_CACHE="cache";

    /**
     * 操作日志
     */
	private static final String FILE_ACTIONS_LOG="Actions Log";

    /**
     * 下载
     */
	private static final String FILE_DOWNLOAD="Download";

    /**
     * 头像
     */
	private static final String FILE_AVATAR="Avatar";

	/**
	 * 图片
	 */
	private static final String FILE_PICTURE="Picture";

    /**
     * 错误日志
     */
	private static final String FILE_CRASH_LOG="Crash Log";
	
	public static String rootFileName=null;
	
	private AppDataFile(String rootFileName) {
        if(sdcard==null){
            sdcard=Environment.getDataDirectory();
        }
        File rootFile=new File(sdcard,FILE_ROOT_PATH);
        if(!rootFile.exists()){
            rootFile.mkdirs();
        }

        mAppFile=new File(rootFile,rootFileName);
        if(!mAppFile.exists()){
            mAppFile.mkdirs();
        }

		makFile(mAppFile,rootFileName);
	}
	
	public static void init(String rootFileName ){
		if(mAppDataFile ==null){
			mAppDataFile =new AppDataFile(rootFileName);
		}
	}
	
	private void makFile(File appFile,String rootFileName){
        makChildFile(appFile,FILE_CACHE);
		makChildFile(appFile,FILE_ACTIONS_LOG);
        makChildFile(appFile,FILE_DOWNLOAD);
        makChildFile(appFile,FILE_AVATAR);
		makChildFile(appFile,FILE_PICTURE);
        makChildFile(appFile,FILE_CRASH_LOG);
	}

    private File makChildFile(File appFile,String filename){
        File file=new File(appFile,filename);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    private static File getAppFile(){
		if (mAppDataFile.mAppFile != null) {
			return mAppDataFile.mAppFile;
		} else {
			throw new IllegalStateException("AppDataFile未初始化");
		}
    }

	/**
	 * 获取缓存文件目录
	 * @return
	 */
	public static File getCacheFile(){
		File file=new File(getAppFile(),FILE_CACHE);
		if(!file.exists()){
			file.mkdirs();
		}	
		return file;
	}
	
	/**
	 * 获取行为日志文件目录
	 * @return
	 */
	public static File getActionsLogFile(){
		File file=new File(getAppFile(),FILE_ACTIONS_LOG);
		if(!file.exists()){
			file.mkdirs();
		}	
		return file;
	}
	
	/**
	 * 获取下载文件目录
	 * @return
	 */
	public static File getDownloadFile(){
        File file=new File(getAppFile(),FILE_DOWNLOAD);
		if(!file.exists()){
			file.mkdirs();
		}	
		return file;
	}
	
	/**
	 * 获取头像目录
	 * @return
	 */
	public static File getAvatarFile(){
        File file=new File(getAppFile(),FILE_AVATAR);
		if(!file.exists()){
			file.mkdirs();
		}	
		return file;
	}

	/**
	 * 获取图片目录
	 * @return
	 */
	public static File getPictureFile(){
		File file=new File(getAppFile(),FILE_PICTURE);
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 获取异常日志目录
	 * @return
	 */
	public static File getCrashLogFile(){
        File file=new File(getAppFile(),FILE_CRASH_LOG);
		if(!file.exists()){
			file.mkdirs();
		}	
		return file;
	}
}
