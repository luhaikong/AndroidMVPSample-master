package com.wuxiaolong.androidmvpsample.utils;

import java.nio.charset.Charset;

public class Base64Util {

    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 字节流转换为base64字符串
     * @param src
     * @return
     */
    public static String encode(byte [] src){
        return android.util.Base64.encodeToString(src, android.util.Base64.NO_WRAP);
    }

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 按指定编码将源字符串转为base64字符串
     * @param src
     * @param charset
     * @return
     */
    public static String encode(String src, Charset charset){
        return encode(src.getBytes(charset));
    }

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 将源字符串转为base64字符串
     * @param src
     * @return
     */
    public static String encode(String src){
        return encode(src,DEFAULT_CHARSET);
    }

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc base64字符串转为字节数组
     * @param src
     * @return
     */
    public static byte [] decode(String src){
        return android.util.Base64.decode(src, android.util.Base64.NO_WRAP);
    }

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 按指定编码将base64字符串转为目标字符串
     * @param base64
     * @param charset
     * @return
     */
    public static String decodeToString(String base64, Charset charset){
        return new String(decode(base64),charset);
    }

    /**
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 将base64字符串转为目标字符串
     * @param base64
     * @return
     */
    public static String decodeToString(String base64){
        return decodeToString(base64,DEFAULT_CHARSET);
    }

    /**
     * Base64解码
     *
     * @param input 要解码的字符串
     * @return Base64解码后的字符串
     */
    public static byte[] base64Decode(byte[] input) {
        return android.util.Base64.decode(input, android.util.Base64.NO_WRAP);
    }

}
