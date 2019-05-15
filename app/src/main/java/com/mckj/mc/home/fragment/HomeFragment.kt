package com.mckj.mc.home.fragment

import android.os.Bundle
import android.view.View

import com.mckj.mc.R
import com.mckj.mc.home.contract.HomeFragmentContract
import com.mckj.mc.home.presenter.HomeFragmentPresenter
import com.mckj.tec_library.base.BaseFragment


/**
 *
 */
class HomeFragment : BaseFragment<HomeFragmentContract.View, HomeFragmentContract.Presenter>(),HomeFragmentContract.View {
    override val layoutId = R.layout.fragment_home
    override fun createPresenter(): HomeFragmentContract.Presenter = HomeFragmentPresenter()

    companion object {
        /**
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    override fun init(view: View, savedInstanceState: Bundle?) {

    }


}
