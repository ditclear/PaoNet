package com.ditclear.paonet.di.component

import com.ditclear.paonet.ContentFragment
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.view.article.ArticleDetailActivity
import dagger.Component
import javax.inject.Singleton

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/29.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent{
    fun inject(fragment: ContentFragment)

    fun inject(activity: ArticleDetailActivity)
}