package com.mckj.tec_library.base

import android.content.Context
import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */

abstract class BasePresenter<V : BaseView> : IBasePresenter<V> {
    var mViewRef: WeakReference<V>? = null
    protected var mContextRef: Reference<Context>? = null


    override fun attachModelView(pView: V, context: Context?) {
        mViewRef = WeakReference<V>(pView)
        this.mContextRef = WeakReference(context)
        init()
    }

    protected open fun init() {

    }


    override fun getView(): V? {
        return if (isAttach()) {
            mViewRef!!.get()
        } else {
            null
        }
    }

    override fun isAttach(): Boolean {
        return null != mViewRef && null != mViewRef!!.get()
    }

    /**
     * 获取Context
     *
     * @return
     */
    override fun getContext(): Context? {
        return mContextRef!!.get()
    }

    /**
     * 判断是否与View建立了关联
     *
     * @return
     */
    override fun isViewAttached(): Boolean {
        return mViewRef != null && mViewRef!!.get() != null
    }


    override fun onDettach() {
        if (null != mViewRef) {
            mViewRef!!.clear()
            mViewRef = null
        }
        if (mContextRef != null) {
            mContextRef!!.clear()
            mContextRef = null
        }
    }
}