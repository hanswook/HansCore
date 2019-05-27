package com.mckj.mc.home.contract

import com.mckj.tec_library.base.BasePresenter
import com.mckj.tec_library.base.BaseView
import com.mckj.tec_library.base.IBasePresenter

/**
 * @author Hans
 * @create 2019/5/15
 * @description
 */
interface HomeFragmentContract {

    interface Presenter : IBasePresenter<View> {

        fun one()

        fun two()

    }

    interface View : BaseView {

    }
}