package com.mckj.tec_library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mckj.tec_library.utils.ToastUtil
import org.greenrobot.eventbus.EventBus

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */


abstract class BaseFragment<V, T : IBasePresenter<V>> : BaseRxFragment(), BaseView {

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
            mPresenter!!.attachModelView(this as V, context)
        }
        dialogManager = LoadingDialogManager(activity)

        (rootView?.parent as ViewGroup?)?.removeView(rootView)
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

}
