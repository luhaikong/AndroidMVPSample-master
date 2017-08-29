package com.common.lhk.library.preference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import java.util.Set;

/**
 * 使用方法：
 * SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
 * BasePreferences basePreferences = new BasePreferences(preferences);
 */
public class BasePreferences {

    private SharedPreferences mPreferences;

    public BasePreferences(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
    }

    public boolean putInt(String key, int value) {
        return mPreferences.edit().putInt(key, value).commit();
    }

    public void putApplyInt(String key, int value){
        mPreferences.edit().putInt(key, value).apply();
    }

    public boolean putLong(String key, long value) {
        return mPreferences.edit().putLong(key, value).commit();
    }

    public void putApplyLong(String key, long value) {
        mPreferences.edit().putLong(key, value).apply();
    }

    public boolean putFloat(String key, float value) {
        return mPreferences.edit().putFloat(key, value).commit();
    }

    public void putApplyFloat(String key, float value) {
        mPreferences.edit().putFloat(key, value).apply();
    }

    public boolean putString(String key, String value) {
        return mPreferences.edit().putString(key, value).commit();
    }

    public void putApplyString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public boolean putSetString(String key, Set<String> value) {
        return mPreferences.edit().putStringSet(key, value).commit();
    }

    public void putApplySetString(String key, Set<String> value) {
        mPreferences.edit().putStringSet(key, value).apply();
    }

    public boolean putBoolean(String key, boolean value) {
        return mPreferences.edit().putBoolean(key, value).commit();
    }

    public void putApplyBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue){
        return mPreferences.getFloat(key, defValue);
    }

    public Set<String> getSetString(String key, Set<String> defValue){
        return mPreferences.getStringSet(key, defValue);
    }

    public boolean contains(String key){
        return mPreferences.contains(key);
    }

    public void registerOnChangeListener(OnSharedPreferenceChangeListener listener){
        if (listener!=null)
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterOnChangeListener(OnSharedPreferenceChangeListener listener){
        if (listener!=null)
        mPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }


}
