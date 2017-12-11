package com.njqg.orchard.library_core.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by hans
 * date: 2017/11/23 17:17.
 * e-mail: hxxx1992@163.com
 */
public class Constant {
    //    public static String BASE_URL = "http://192.168.11.56:28080";  //本地
    public static String BASE_URL = "http://gank.io";  //gank

    public static boolean IS_FULL_SCREEN = false;

    public static boolean IS_DEBUG_MODE = true;

    public static String PATH_CACHE = Environment.getExternalStorageDirectory().getPath() + File.separator + "orchard";

}
