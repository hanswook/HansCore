package com.mckj.tec_library.base

import android.app.Application
import android.content.Context
import com.mckj.tec_library.utils.SPUtil
import com.mckj.tec_library.utils.logger.CustomLogCatStrategy
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */


abstract class BaseApplication : Application() {

    abstract fun isDebug(): Boolean


    override fun onCreate() {
        super.onCreate()
        initLogger()
        SPUtil.getInstance().init(this)
    }

    /**
     * 初始化Logger
     */
    private fun initLogger() {
        if (isDebug()) {
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(1)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(CustomLogCatStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("mc")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        }
    }



}
