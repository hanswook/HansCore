package com.mckj.tec_library.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class YJYSPUtils {

    // SharedPreferences
    public static final String SP_NAME = "visitshanghai.com";
    static SharedPreferences sp;
    static SharedPreferences.Editor editor;

    private static void instance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
    }

    public static void put(Context context, String key, String arg) {
        instance(context);
        editor = sp.edit();
        editor.putString(key, arg);
        editor.commit();

    }

    public static void put(Context context, String key, boolean arg) {
        instance(context);
        editor = sp.edit();
        editor.putBoolean(key, arg);
        editor.commit();

    }

    public static void put(Context context, String key, int val) {
        instance(context);
        editor = sp.edit();
        editor.putInt(key, val);
        editor.commit();

    }

    public static void put(Context context, String key, long val) {
        instance(context);
        editor = sp.edit();
        editor.putLong(key, val);
        editor.commit();

    }

    /**
     * 根据key值获取保存在sp的字符串
     *
     * @param key
     * @param defValue 默认值
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        instance(context);
        String value = sp.getString(key, defValue);

        return value;
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        instance(context);
        boolean value = sp.getBoolean(key, defValue);

        return value;
    }

    public static int getInt(Context context, String key, int defValue) {
        instance(context);
        int value = sp.getInt(key, defValue);

        return value;
    }

    public static long getLong(Context context, String key, long defValue) {
        instance(context);
        long value = sp.getLong(key, defValue);

        return value;
    }

    public static void remove(Context context, String key) {
        instance(context);
        editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}