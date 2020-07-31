package com.hans.xlib.utils

import android.app.Application
import android.view.Gravity
import com.hans.xlib.R
import com.hjq.toast.ToastUtils

/**
 *
 * @date:     2020/7/31 10:48 AM
 * @author: hanxu
 * @email hxxx1992@163.com
 * @description null
 */

object UtilsInitiator {
    fun toastInit(application: Application) {
        try {
            ToastUtils.init(application)
            ToastUtils.setView(R.layout.xlib_base_ui_toast_popup)
            ToastUtils.setGravity(Gravity.CENTER, 0, -DensityUtil.dip2px(65f, application))
        } catch (ex1: java.lang.Exception) {
            ex1.printStackTrace()
        }
    }
}