package com.mckj.tec_library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mckj.tec_library.utils.ToastUtil
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */


abstract class BaseDialogFragment<V:BaseView, T : IBasePresenter<V>> : DialogFragment(), BaseView {

    private var disposables2Stop: CompositeDisposable? = null// 管理Stop取消订阅者者
    private var disposables2Destroy: CompositeDisposable? = null// 管理Destroy取消订阅者者

    protected var mPresenter: T? = null

    protected var rootView: View? = null

    private var dialogManager: LoadingDialogManager? = null


    protected abstract val layoutId: Int

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected open fun isRegisterEventBus(): Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(this.layoutId, container, false)
        }
        if (isRegisterEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        mPresenter = createPresenter()
        if (null != mPresenter) {
            mPresenter!!.attachModelView(this as V, getContext())
        }
        dialogManager = LoadingDialogManager(getActivity())

        val parent = rootView!!.parent as ViewGroup
        parent?.removeView(rootView)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view, savedInstanceState)
    }

    protected abstract fun createPresenter(): T

    protected abstract fun init(view: View, savedInstanceState: Bundle?)

    override fun onDestroyView() {
        if (null != mPresenter) {
            mPresenter!!.onDettach()
        }
        if (isRegisterEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        super.onDestroyView()
    }

    override fun showLoading() {
        dialogManager!!.showLoadingDialog()
    }

    override fun hideLoading() {
        dialogManager!!.dismissLoadingDialog()
    }

    override fun showToast(content: String) {
        ToastUtil.showToast(context, content)
    }

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