package com.hans.xlib.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * 日志打印类
 *
 * @author: LiBing.
 * @date: 2017/10/27.
 * @version: V1.0.0.
 */
object LogUtils {
    private val logEnable: Boolean
        private get() = Constant.DEBUG

    /**
     * 初始化Logger
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1) // (Optional) How many method line to show. Default 2
            .methodOffset(7) // (Optional) Hides internal method calls up to offset. Default 5
            .logStrategy(CustomLogCatStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
            .tag("mc") // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    fun d(`object`: Any?) {
        if (logEnable) {
            Logger.d(`object`)
        }
    }

    fun d(tag: String?, `object`: Any?) {
        if (logEnable) {
            Logger.d(`object`)
        }
    }

    fun e(tag: String?, message: String?) {
        if (logEnable) {
            Logger.e(message!!, "")
        }
    }

    fun json(tag: String?, jsonData: String?) {
        if (logEnable) {
            Logger.json(jsonData)
        }
    }

    fun xml(tag: String?, xmlData: String?) {
        if (logEnable) {
            Logger.xml(xmlData)
        }
    }

    fun d(msg: String?) {
        if (logEnable) {
            Logger.d(msg)
        }
    }

    fun v(msg: String?) {
        if (logEnable) {
            Logger.v(msg!!)
        }
    }

    fun i(msg: String?) {
        if (logEnable) {
            Logger.i(msg!!)
        }
    }

    fun e(msg: String?) {
        if (logEnable) {
            Logger.e(msg!!)
        }
    }

    fun e(throwable: Throwable?) {
        if (logEnable) {
            Logger.e(
                throwable,
                if (throwable != null) throwable.message!! else ""
            )
        }
    }

    fun w(msg: String?) {
        if (logEnable) {
            Logger.w(msg!!)
        }
    }

    fun d(throwable: Throwable?) {
        if (logEnable) {
            Logger.e(
                throwable,
                if (throwable != null) throwable.message!! else ""
            )
        }
    }

    fun json(jsonStr: String?) {
        if (logEnable) {
            Logger.json(jsonStr)
        }
    }
}