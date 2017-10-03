package com.ditclear.paonet.di.component

import com.ditclear.paonet.di.module.ActivityModule
import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.view.article.ArticleDetailActivity
import dagger.Subcomponent

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/29.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: ArticleDetailActivity)

    fun plus(module: FragmentModule):FragmentComponent
}