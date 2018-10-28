package com.ditclear.paonet.view.mine

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.ditclear.paonet.view.search.SearchFragment

/**
 * 页面描述：SinglePageActivity
 *
 * Created by ditclear on 2018/10/9.
 */
interface SinglePageActivity {



    fun needShowTab(b: Boolean)

    fun getSearchFragment(): SearchFragment?

    fun setupWithViewPager(viewPager: ViewPager)

    fun getTopFragment(): Fragment?

    val aboutModuleId :Int
    fun navigateToAboutModule()
}