package com.mckj.tec_library.base

import android.os.Bundle
import android.view.View

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */

abstract class BaseLazyFragment<V, T : IBasePresenter<V>> : BaseFragment<V, T>() {

    protected var mIsViewInitiated: Boolean = false
    protected var mIsVisibleToUser: Boolean = false
    protected var mIsDataInitiated: Boolean = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
        initFetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mIsViewInitiated = true
        initFetchData()
    }
    private fun initFetchData() {
        if (mIsVisibleToUser && mIsViewInitiated && !mIsDataInitiated) {
            fetchData()
            mIsDataInitiated = true
        }
    }

    protected abstract fun fetchData()
}
