package com.ditclear.paonet.di.component

import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.view.ui.article.ArticleListFragment
import com.ditclear.paonet.view.ui.code.CodeListFragment
import com.ditclear.paonet.view.ui.home.HomeFragment
import com.ditclear.paonet.view.ui.home.RecentFragment
import com.ditclear.paonet.view.ui.mine.CollectionListFragment
import com.ditclear.paonet.view.ui.mine.MyArticleFragment
import com.ditclear.paonet.view.ui.mine.MyCollectFragment
import com.ditclear.paonet.view.ui.search.RecentSearchFragment
import com.ditclear.paonet.view.ui.search.SearchResultFragment
import dagger.Subcomponent

/**
 * 页面描述：FragmentComponent
 *
 * Created by ditclear on 2017/9/29.
 */
@FragmentScope
@Subcomponent(modules = arrayOf(FragmentModule::class))
interface FragmentComponent {

    fun inject(fragment: ArticleListFragment)
    fun inject(fragment: CodeListFragment)

    fun inject(fragment: CollectionListFragment)

    fun inject(fragment: MyCollectFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: RecentFragment)


    fun inject(fragment: SearchResultFragment)

    fun inject(fragment: RecentSearchFragment)

    fun inject(fragment: MyArticleFragment)

    @Subcomponent.Builder
    interface Builder {
        fun fragmentModule(module: FragmentModule): Builder

        fun build(): FragmentComponent
    }
}