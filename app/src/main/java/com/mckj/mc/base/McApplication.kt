package com.mckj.mc.base

import android.app.Application
import com.hans.xlib.utils.UtilsInitiator


/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
class McApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        initDependecy()
    }

    fun initDependecy() {
        UtilsInitiator.toastInit(this)
    }
}