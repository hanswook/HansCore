package com.mckj.tec_library.base

import io.reactivex.disposables.Disposable

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
interface BaseRxView {
    fun addRxDestroy(disposable: Disposable): Boolean

    fun addRxStop(disposable: Disposable): Boolean

    fun remove(disposable: Disposable)
}