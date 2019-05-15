package com.mckj.tec_library.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
public abstract class BaseRxFragment : Fragment(), BaseRxView {

    private var disposables2Stop: CompositeDisposable? = null// 管理Stop取消订阅者者
    private var disposables2Destroy: CompositeDisposable? = null// 管理Destroy取消订阅者者


    override fun addRxStop(disposable: Disposable): Boolean {
        if (disposables2Stop == null) {
            throw IllegalStateException(
                "addUtilStop should be called between onStart and onStop"
            )
        }
        disposables2Stop!!.add(disposable)
        return true
    }

    override fun addRxDestroy(disposable: Disposable): Boolean {
        if (disposables2Destroy == null) {
            throw IllegalStateException(
                "addUtilDestroy should be called between onCreate and onDestroy"
            )
        }
        disposables2Destroy!!.add(disposable)
        return true
    }

    override fun remove(disposable: Disposable) {
        if (disposables2Stop == null && disposables2Destroy == null) {
            throw IllegalStateException("remove should not be called after onDestroy")
        }
        if (disposables2Stop != null) {
            disposables2Stop!!.remove(disposable)
        }
        if (disposables2Destroy != null) {
            disposables2Destroy!!.remove(disposable)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (disposables2Destroy != null) {
            throw IllegalStateException("onCreate called multiple times")
        }
        disposables2Destroy = CompositeDisposable()
    }


    override fun onStart() {
        super.onStart()
        if (disposables2Stop != null) {
            throw IllegalStateException("onStart called multiple times")
        }
        disposables2Stop = CompositeDisposable()
    }

    override fun onStop() {
        super.onStop()
        if (disposables2Stop == null) {
            throw IllegalStateException("onStop called multiple times or onStart not called")
        }
        disposables2Stop!!.dispose()
        disposables2Stop = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposables2Destroy == null) {
            throw IllegalStateException(
                "onDestroy called multiple times or onCreate not called"
            )
        }
        disposables2Destroy!!.dispose()
        disposables2Destroy = null
    }
}