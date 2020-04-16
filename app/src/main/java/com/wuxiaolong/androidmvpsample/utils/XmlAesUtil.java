package com.wuxiaolong.androidmvpsample.utils;

import com.wuxiaolong.androidmvpsample.encrypt.AESUtil;
import com.wuxiaolong.androidmvpsample.encrypt.Base64Util;
import com.wuxiaolong.androidmvpsample.encrypt.MD5Util;

/**
 * Created by Administrator on 2019/7/8.
 * 为了加解密XML资源文件中的铭感信息，做了封装
 */

public class XmlAesUtil {

    public final static String PUBLIC_KEY = "ynyx2019";

    /**
     * 解密
     * @param content
     * @param password
     * @return
     */
    public static String decrypt(String content, String password){
        byte[] bytes = Base64Util.decode(content);
        String secKey = MD5Util.hash(password);
        byte [] aesData = AESUtil.decrypt(bytes,secKey);
        if (aesData!=null){
            return new String(aesData);
        } else {
            return null;
        }
    }

    /**
     * 加密
     * @param content
     * @param password
     * @return
     */
    public static String encrypt(String content, String password){
        String secKey = MD5Util.hash(password);
        byte [] aesData = AESUtil.encrypt(content.getBytes(),secKey);
        if (aesData!=null){
            return  Base64Util.encode(aesData);
        } else {
            return null;
        }
    }
}
