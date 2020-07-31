package com.mckj.mc.base

import android.app.Application


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
    }
}