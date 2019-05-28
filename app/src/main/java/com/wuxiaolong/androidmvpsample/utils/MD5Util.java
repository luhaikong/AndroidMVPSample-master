package com.wuxiaolong.androidmvpsample.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Date 2019/03/09
 * @Author 杨俊刚
 * @Desc 摘要计算工具类
 */
public class MD5Util {

    /**默认编码*/
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 计算摘要
     * @param str
     * @return
     */
    public static String hash(String str) {
        return hash(str,DEFAULT_CHARSET);
    }

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 按指定编码类型计算摘要
     * @param str
     * @param str
     * @param charset
     * @return
     */
    public static String hash(String str, Charset charset){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert messageDigest != null;
        messageDigest.update(str.getBytes(charset));
        return RegulationUtil.byteToHex(messageDigest.digest()).toUpperCase();
    }



}
