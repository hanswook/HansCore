package com.hans.xlib.utils

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * @date:     2020/7/31 10:37 AM
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */
object SPUtils {
    // SharedPreferences
    const val SP_NAME = "default"
    var sp: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    private fun instance(context: Context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0)
        }
    }

    fun put(
        context: Context,
        key: String?,
        arg: String?
    ) {
        instance(context)
        editor = sp!!.edit()
        editor?.putString(key, arg)
        editor?.commit()
    }

    fun put(
        context: Context,
        key: String?,
        arg: Boolean
    ) {
        instance(context)
        editor = sp!!.edit()
        editor?.putBoolean(key, arg)
        editor?.commit()
    }

    fun put(context: Context, key: String?, `val`: Int) {
        instance(context)
        editor = sp!!.edit()
        editor?.putInt(key, `val`)
        editor?.commit()
    }

    fun put(
        context: Context,
        key: String?,
        `val`: Long
    ) {
        instance(context)
        editor = sp!!.edit()
        editor?.putLong(key, `val`)
        editor?.commit()
    }

    /**
     * 根据key值获取保存在sp的字符串
     *
     * @param key
     * @param defValue 默认值
     * @return
     */
    fun getString(
        context: Context,
        key: String?,
        defValue: String?
    ): String? {
        instance(context)
        return sp!!.getString(key, defValue)
    }

    fun getBoolean(
        context: Context,
        key: String?,
        defValue: Boolean
    ): Boolean {
        instance(context)
        return sp!!.getBoolean(key, defValue)
    }

    fun getInt(context: Context, key: String?, defValue: Int): Int {
        instance(context)
        return sp!!.getInt(key, defValue)
    }

    fun getLong(
        context: Context,
        key: String?,
        defValue: Long
    ): Long {
        instance(context)
        return sp!!.getLong(key, defValue)
    }

    fun remove(context: Context, key: String?) {
        instance(context)
        editor = sp!!.edit()
        editor?.remove(key)
        editor?.commit()
    }
}