package com.ditclear.paonet.di.component

import com.ditclear.paonet.di.module.FragmentModule
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.view.article.ArticleListFragment
import com.ditclear.paonet.view.code.CodeListFragment
import com.ditclear.paonet.view.home.HomeFragment
import com.ditclear.paonet.view.home.RecentFragment
import com.ditclear.paonet.view.mine.CollectionListFragment
import com.ditclear.paonet.view.mine.MyArticleFragment
import com.ditclear.paonet.view.mine.MyCollectFragment
import com.ditclear.paonet.view.search.RecentSearchFragment
import com.ditclear.paonet.view.search.SearchResultFragment
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
}