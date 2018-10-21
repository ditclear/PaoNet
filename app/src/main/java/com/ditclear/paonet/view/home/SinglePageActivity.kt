package com.ditclear.paonet.view.home

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.SinglePageActivityBinding
import com.ditclear.paonet.helper.SystemBarHelper
import com.ditclear.paonet.view.base.BaseActivity
import com.ditclear.paonet.view.search.SearchFragment

/**
 * 页面描述：SinglePageActivity
 *
 * Created by ditclear on 2018/10/9.
 */
class SinglePageActivity : BaseActivity<SinglePageActivityBinding>() {
    override fun loadData(isRefresh: Boolean) {

    }

    override fun initView() {
//        val finalHost = NavHostFragment.create(R.navigation.navigation)
//        supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.nav_host,finalHost)
//                .setPrimaryNavigationFragment(finalHost)
//                .commit()

        SystemBarHelper.immersiveStatusBar(this,0f)

    }

    override fun getLayoutId(): Int = R.layout.single_page_activity

    private var isQuit = false

//    override fun onBackPressed() {
//        if (!Navigation.findNavController(this, R.id.nav_host).navigateUp()) {
//
//            if (needCloseDrawer()) {
//
//            } else if (!isQuit) {
//                toast(msg = "再按一次退出程序")
//                isQuit = true;
//                //在两秒钟之后isQuit会变成false
//                Single.just(isQuit)
//                        .async(2000)
//                        .bindLifeCycle(this)
//                        .subscribe({ isQuit = false }, {})
//            } else {
//                super.onBackPressed()
//            }
//        }
//    }

    override fun onSupportNavigateUp() = Navigation.findNavController(this, R.id.nav_host).navigateUp()

    fun needShowTab(b: Boolean) {
        val f = getTopFragment()
        if (f is MainFragment) {
            f.needShowTab(b)
        }
    }

    fun getSearchFragment(): SearchFragment? {
        val f = getTopFragment()
        if (f is SearchFragment) {
            return f
        }
        return null
    }

    private fun needCloseDrawer(): Boolean {
        val f = getTopFragment()
        if (f is MainFragment) {
            return f.needCloseDrawer()
        }
        return false
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        val f = supportFragmentManager.fragments.firstOrNull()?.childFragmentManager?.fragments?.find {
            it is MainFragment
        }
        (f as MainFragment?)?.setupWithViewPager(viewPager)
    }

    fun getTopFragment(): Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host)?.childFragmentManager?.fragments?.firstOrNull()

}