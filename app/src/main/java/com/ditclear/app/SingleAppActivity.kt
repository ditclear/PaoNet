package com.ditclear.app

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import androidx.navigation.Navigation
import com.ditclear.paonet.databinding.SinglePageActivityBinding
import com.ditclear.paonet.helper.SystemBarHelper
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.view.base.BaseActivity
import com.ditclear.paonet.view.home.MainFragment
import com.ditclear.paonet.view.mine.SinglePageActivity
import com.ditclear.paonet.view.search.SearchFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * 页面描述：SinglePageActivity
 *
 * Created by ditclear on 2018/10/9.
 */
class SingleAppActivity : BaseActivity<SinglePageActivityBinding>(), SinglePageActivity {


    override fun loadData(isRefresh: Boolean) {

    }

    override fun initView() {

        SystemBarHelper.immersiveStatusBar(this, 0f)

        mExitSubject
                .doOnNext {
                    toast(msg = "再按一次退出程序")
                    isQuit = true
                }
                .subscribeOn(Schedulers.io())
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isQuit = false }


        Navigation.findNavController(this,R.id.nav_host)
                .addOnNavigatedListener { controller, destination ->
                    if (destination.id == R.id.mainFragment){

                    }
                }
    }

    override fun getLayoutId(): Int = R.layout.single_page_activity

    private var isQuit = false

    override fun onBackPressed() {
        if (getTopFragment() is MainFragment) {
            if (!needCloseDrawer()) {
                exitByDoubleClick()
            }
        } else {
            super.onBackPressed()
        }
    }

    private val mExitSubject by lazy {
        PublishSubject.create<Boolean>()


    }

    private fun exitByDoubleClick() {
        if (!isQuit) {
            //在两秒钟之后isQuit会变成false
            mExitSubject.onNext(isQuit)
        } else {
            super.onBackPressed()
        }
    }

    override fun needShowTab(b: Boolean) {
        val f = getTopFragment()
        if (f is MainFragment) {
            f.needShowTab(b)
        }
    }

    override fun getSearchFragment(): SearchFragment? {
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

    override fun setupWithViewPager(viewPager: ViewPager) {
        val f = supportFragmentManager.fragments.firstOrNull()?.childFragmentManager?.fragments?.find {
            it is MainFragment
        }
        (f as MainFragment?)?.setupWithViewPager(viewPager)
    }

    override fun getTopFragment(): Fragment? = supportFragmentManager.findFragmentById(R.id.nav_host)?.childFragmentManager?.fragments?.firstOrNull()

    override val aboutModuleId: Int = R.navigation.about_navigation

    override fun navigateToAboutModule(){

        Navigation.findNavController(this, R.id.nav_main_host).navigate(R.id.aboutFragment)


    }
}