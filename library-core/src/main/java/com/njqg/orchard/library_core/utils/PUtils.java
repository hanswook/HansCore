package com.njqg.orchard.library_core.utils;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by hans
 * date: 2018/3/13 19:11.
 * e-mail: hxxx1992@163.com
 */

class PUtils {
    static String getProperties(Context context, String string) {
        String value = "";
        Properties properties = new Properties();
        try {
            properties.load(context.getAssets().open("abc.properties"));
            value = properties.getProperty(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
