package com.wuxiaolong.androidmvpsample.utils;

import android.os.Build;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法
    private static final String ivParameter = "1111111111111111";

    public static byte[] encrypt(byte[] content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM,"BC");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec(password), getIvParameterSpec());// 初始化
            return cipher.doFinal(content); // 加密
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] decrypt(byte[] content, String password) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM,"BC");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec(password), getIvParameterSpec());// 初始化
            return cipher.doFinal(content); // 加密
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SecretKeySpec getSecretKeySpec(String password) throws Exception {
//        return new SecretKeySpec(getRawKey(password.getBytes()), KEY_ALGORITHM);
        return new SecretKeySpec(password.getBytes(), KEY_ALGORITHM);
    }

    private static IvParameterSpec getIvParameterSpec(){
        return new IvParameterSpec(ivParameter.getBytes());
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom sr;
        if(Build.VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        }
        sr.setSeed(seed);
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    public static final class CryptoProvider extends Provider {
        public CryptoProvider() {
            super("Crypto", 1.0D, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            this.put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            this.put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
