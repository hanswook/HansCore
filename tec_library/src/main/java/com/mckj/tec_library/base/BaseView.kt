package com.mckj.tec_library.base

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
interface BaseView : BaseRxView {
    fun showLoading()

    fun hideLoading()

    fun showToast(content: String)
}