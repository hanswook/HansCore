package com.mckj.tec_library.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * description
 *
 * @author created by wuwang on 2016/5/25
 */
public class SPUtil {

    private static SPUtil instance;
    private SharedPreferences sp;
    private final String defaultFlag = "wxy";
    private static final String HOME_ADVERT = "home_advert";
    private static final String USER_ID = "user_id";

    private SPUtil() {
    }

    public static SPUtil getInstance() {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    instance = new SPUtil();
                }
            }
        }
        return instance;
    }

    public void init(Application application) {
        sp = application.getSharedPreferences(application.getPackageName(), Context.MODE_PRIVATE);
    }

    //清空数据
    public void clean() {
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            editor.commit();
        }
    }

    public void setFlag(String flag, boolean bool) {
        sp.edit().putBoolean(flag, bool).apply();
    }

    public boolean getFlag(String flag, boolean defaultFlag) {
        return sp.getBoolean(flag, defaultFlag);
    }

    public void markFirst() {
        setFlag(defaultFlag, false);
    }

    public boolean isFirst() {
        return sp.getBoolean(defaultFlag, true);
    }

    public void setCache(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public void setCache(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public void setCache(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public String getCache(String key) {
        return sp.getString(key, null);
    }

    public long getCacheLong(String key) {
        return sp.getLong(key, System.currentTimeMillis());
    }

    public long getCache(String key, long value) {
        return sp.getLong(key, value);
    }

    public int getCache(String key, int value) {
        return sp.getInt(key, value);
    }

    public void setCache(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getCache(String key, boolean value) {
        return sp.getBoolean(key, value);
    }

    public void remove(String key) {
        if (sp != null) {

            SharedPreferences.Editor editor = sp.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public void setStateDiscount(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public int getStateDisCount(String key) {
        return sp.getInt(key, 2);
    }

    public void setHomeAdvert(String value) {
        sp.edit().putString(HOME_ADVERT, value).apply();
    }


    public String getHomeAdvert() {
        return sp.getString(HOME_ADVERT, "");
    }

    public void saveUserId(int userId) {
        sp.edit().putInt(USER_ID, userId).apply();
    }

    public int getUserId() {
        return sp.getInt(USER_ID, -1);
    }
    public void removeUserId() {
        if (sp != null) {
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(USER_ID);
            editor.apply();
        }
    }
}
