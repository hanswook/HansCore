package com.mckj.mc.base

import com.mckj.tec_library.base.BaseApplication
import com.mckj.tec_library.base.DebugConstant
import com.zxy.tiny.Tiny
import io.paperdb.Paper

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
class McApplication : BaseApplication() {
    override fun isDebug() = DebugConstant.DEBUG


    override fun onCreate() {
        super.onCreate()
        initDependecy()
    }

    fun initDependecy() {
        Paper.init(this)
        Tiny.getInstance().init(this)
    }
}