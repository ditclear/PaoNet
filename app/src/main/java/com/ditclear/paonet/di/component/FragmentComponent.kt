package com.ditclear.paonet.di.component

import com.ditclear.paonet.ContentFragment
import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.view.article.ArticleListFragment
import dagger.Subcomponent

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/29.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: ContentFragment)
    fun inject(fragment: ArticleListFragment)
}