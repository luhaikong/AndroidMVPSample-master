package com.common.lhk.library.Preference;

import android.content.SharedPreferences;

public class BasePreferences {

    protected SharedPreferences mPreferences;

    protected BasePreferences(SharedPreferences sharedPreferences) {
        mPreferences = sharedPreferences;
    }

    public boolean putInt(String key, int value) {
        return mPreferences.edit().putInt(key, value).commit();
    }

    public boolean putLong(String key, long value) {
        return mPreferences.edit().putLong(key, value).commit();
    }

    public boolean putString(String key, String value) {
        return mPreferences.edit().putString(key, value).commit();
    }

    public boolean putBoolean(String key, boolean value) {
        return mPreferences.edit().putBoolean(key, value).commit();
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
}
