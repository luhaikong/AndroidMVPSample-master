package com.wuxiaolong.androidmvpsample.utils;

import java.nio.ByteBuffer;

/**
 * @Date 2019/03/09
 * @Author 杨俊刚
 * @Desc 进制转换工具类
 */
public class RegulationUtil {


    /**
     * @param bytes
     * @return
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 二进制转为十六进制
     */
    public static String byteToHex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            stringBuffer.append((temp.length() == 1) ? "0" + temp : temp);
        }
        return stringBuffer.toString();
    }

    /**
     * @param hex
     * @return
     * @Date 2019/03/09
     * @Author 杨俊刚
     * @Desc 十六进制转为二进制
     */
    public static byte[] hexToByte(String hex) {
        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

}
