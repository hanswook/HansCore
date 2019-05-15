package com.mckj.tec_library.base

import android.content.pm.ActivityInfo
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mckj.tec_library.utils.EventBusUtils
import com.mckj.tec_library.utils.ToastUtil
import com.mckj.tec_library.utils.statusbar.StatusBarUtil

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
abstract class BaseActivity<V, T : IBasePresenter<V>> : BaseRxActivity(), BaseView {

    protected var mPresenter: T? = null
    protected var mActivity: AppCompatActivity? = null

    private var dialogManager: LoadingDialogManager? = null


    protected abstract val layoutId: Int

    /**
     * 沉浸式状态栏-全透明
     *
     * @return
     */
    protected open fun isStatusBar(): Boolean = true

    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected open fun isRegisterEventBus(): Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (isStatusBar()) {
            //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
            StatusBarUtil.setRootViewFitsSystemWindows(this, true)
            //设置状态栏透明
            StatusBarUtil.setTranslucentStatus(this)
            //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
            //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
            if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
                //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
                //这样半透明+白=灰, 状态栏的文字能看得清
                StatusBarUtil.setStatusBarColor(this, Color.TRANSPARENT)
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = getWindow()
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                window.getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.setStatusBarColor(Color.TRANSPARENT)
            }
        }
        setContentView(layoutId)

        if (isRegisterEventBus() && !EventBusUtils.isRegistered(this)) {
            EventBusUtils.register(this)
        }

        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.attachModelView(this as V, this)
        }
        dialogManager = LoadingDialogManager(this)
        mActivity = this
        init()
    }


    protected abstract fun init()

    /**
     * 使用MVP模式时需要重写该方法
     *
     * @return
     */
    protected abstract fun createPresenter(): T


    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.onDettach()
        }
        if (isRegisterEventBus() && EventBusUtils.isRegistered(this)) {
            EventBusUtils.unRegister(this)
        }
    }

    override fun showLoading() {
        dialogManager!!.showLoadingDialog()
    }

    override fun hideLoading() {
        dialogManager!!.dismissLoadingDialog()
    }

    override fun showToast(content: String) {
        ToastUtil.showToast(this, content)
    }

    override fun onResume() {
        /**
         * 设置为竖屏
         */
        if (getRequestedOrientation() !== ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
        super.onResume()
    }

    //适配安卓8.0 透明Activity不能设方向
    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return
        }
        super.setRequestedOrientation(requestedOrientation)
    }

    private fun isTranslucentOrFloating(): Boolean {
        var isTranslucentOrFloating = false
        try {
            val styleableRes =
                Class.forName("com.android.internal.R\$styleable").getField("Window").get(null) as IntArray
            val ta = obtainStyledAttributes(styleableRes)
            val m = ActivityInfo::class.java.getMethod("isTranslucentOrFloating", TypedArray::class.java)
            m.isAccessible = true
            isTranslucentOrFloating = m.invoke(null, ta) as Boolean
            m.isAccessible = false
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return isTranslucentOrFloating
    }


}
