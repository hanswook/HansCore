package com.mckj.mc.home

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.mckj.mc.R
import com.mckj.mc.home.adapter.CommonFragmentAdapter
import com.mckj.mc.home.contract.HomepageContract
import com.mckj.mc.home.fragment.HomeFragment
import com.mckj.mc.home.presenter.HomepagePresenter
import com.mckj.tec_library.base.BaseActivity
import com.mckj.tec_library.base.BaseView
import com.mckj.tec_library.base.IBasePresenter
import kotlinx.android.synthetic.main.home_activity_homepage.*

class HomepageActivity : BaseActivity(), HomepageContract {

    override val layoutId = R.layout.home_activity_homepage
    private val homepagePresenter by lazy { HomepagePresenter() }

    override fun addPresenter() {
        addToPresenters(homepagePresenter)
    }


    companion object {
        fun goToPage(context: Context) {

            context.startActivity(Intent(context, HomepageActivity::class.java))
        }
    }


    private var fragmentAdapter: CommonFragmentAdapter? = null
    private var fragmentList: ArrayList<Fragment> = ArrayList<Fragment>()

    override fun init() {

        initFragment()

        initViewPager()

        homepagePresenter.one()

        homepagePresenter.three()

        homepagePresenter.two()
    }


    private fun initFragment() {
        fragmentList.add(HomeFragment.newInstance())
        fragmentList.add(HomeFragment.newInstance())
        fragmentList.add(HomeFragment.newInstance())
        fragmentList.add(HomeFragment.newInstance())
    }

    private fun initViewPager() {
        fragmentAdapter = CommonFragmentAdapter(supportFragmentManager, fragmentList)
        vp_home.adapter = fragmentAdapter
        vp_home.offscreenPageLimit = 3
    }

}
