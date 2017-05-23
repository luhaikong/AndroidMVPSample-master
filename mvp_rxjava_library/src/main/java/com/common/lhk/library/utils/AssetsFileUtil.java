package com.common.lhk.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地数据加载工具类
 */
public class AssetsFileUtil {

	/**
	 * 返回list列表数据
	 * @param context
	 * @param entityClazz 实体类
	 * @param jsonFilepath json文本路径
	 * @return 返回数据集
	 */
	public static <T> List<T> getListData(Context context,Class<T> entityClazz,String jsonFilepath){
		try{
			if(context!=null){
				InputStream in=context.getAssets().open(jsonFilepath);			
				BufferedReader reader=new BufferedReader(new InputStreamReader(in));
				String temp;
				StringBuffer sb=new StringBuffer();
				while((temp=reader.readLine())!=null){						
					sb.append(temp);
				}
				//Type listType = new TypeToken<List<T>>(){}.getType();
				Type objectType = type(List.class, entityClazz);
				Gson gson = new Gson();
				return gson.fromJson(sb.toString(), objectType);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<T>(0);
	}

	/**
	 * 获取一条数据
	 * @param context
	 * @param entityClazz 实体类
	 * @param jsonFilepath json文本路径
	 * @return 返回实体
	 * @throws IOException
	 */
	public static <T> T getData(Context context,Class<T> entityClazz,String jsonFilepath) throws IOException{
		if(context!=null){
			InputStream in = context.getAssets().open(jsonFilepath);
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			String temp=null;
			StringBuffer sb=new StringBuffer();
			while((temp=reader.readLine())!=null){						
				sb.append(temp);
			}
			Gson gson = new Gson();
			return gson.fromJson(sb.toString(), entityClazz);
		}
		return null;
	}

	/**
	 * 获取本地图片
	 * @param context
	 * @param imagePath 图片路径
	 * @return 返回Bitmap
	 */
	public static Bitmap getBitmap(Context context,String imagePath){
		if(context!=null && !TextUtils.isEmpty(imagePath)){
			Bitmap bitmap=null;
			try {
				bitmap = BitmapFactory.decodeStream(context.getAssets().open(imagePath));
			} catch (Exception e) {
			}
			return bitmap;
		}
		return null;
	}
	
	private static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}
	
}
