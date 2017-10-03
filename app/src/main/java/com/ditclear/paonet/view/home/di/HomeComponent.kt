package com.ditclear.paonet.view.home.di

import com.ditclear.paonet.view.home.HomeFragment
import dagger.Component

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/30.
 */
@Component(modules = arrayOf(HomeModule::class))
interface HomeComponent{

    fun inject(fragment: HomeFragment)
}