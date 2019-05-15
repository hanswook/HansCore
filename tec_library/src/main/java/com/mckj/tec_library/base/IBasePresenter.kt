package com.mckj.tec_library.base

import android.content.Context
import java.lang.ref.WeakReference

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
interface IBasePresenter<V> {

    fun attachModelView(pView: V, context: Context?)

    fun getView(): V?

    fun isAttach(): Boolean

    fun getContext(): Context?

    fun isViewAttached(): Boolean


    fun onDettach()
}