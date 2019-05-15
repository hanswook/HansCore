package com.mckj.tec_library.utils;


import com.mckj.tec_library.base.DebugConstant;
import com.orhanobut.logger.Logger;

/**
 * 日志打印类
 *
 * @author: LiBing.
 * @date: 2017/10/27.
 * @version: V1.0.0.
 */

public class LogUtil {

    private static boolean getLogEnable() {
        return DebugConstant.DEBUG;
    }

    public static void d(Object object) {
        if (getLogEnable()) {
            Logger.d(object);
        }

    }

    public static void d(String tag, Object object) {
        if (getLogEnable()) {
            Logger.d(object);
        }
    }


    public static void e(String tag, String message) {
        if (getLogEnable()) {
            Logger.e(message, "");
        }
    }

    public static void json(String tag, String jsonData) {
        if (getLogEnable()) {
            Logger.json(jsonData);
        }
    }

    public static void xml(String tag, String xmlData) {
        if (getLogEnable()) {
            Logger.xml(xmlData);
        }
    }


    public static void d(String msg) {
        if (getLogEnable()) {
            Logger.d(msg);
        }
    }

    public static void i(String msg) {
        if (getLogEnable()) {
            Logger.i(msg);
        }
    }

    public static void e(String msg) {
        if (getLogEnable()) {
            Logger.e(msg);
        }
    }


    public static void e(Throwable throwable) {
        if (getLogEnable()) {
            Logger.e(throwable, throwable != null ? throwable.getMessage() : "");
        }
    }

    public static void w(String msg) {
        if (getLogEnable()) {
            Logger.w(msg);
        }
    }

    public static void d(Throwable throwable) {
        if (getLogEnable()) {
            Logger.e(throwable, throwable != null ? throwable.getMessage() : "");
        }
    }

    public static void json(String jsonStr) {
        if (getLogEnable()) {
            Logger.json(jsonStr);
        }
    }
}
