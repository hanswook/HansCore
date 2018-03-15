package com.hans.hc.hanscore.libpack;


import com.njqg.orchard.library_core.base.BaseApp;

/**
 * Created by hans
 * date: 2017/11/28 16:37.
 * e-mail: hxxx1992@163.com
 */

public class MyApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        DataBaseHelper.getInstance().setDatabase();
    }
}
