package com.ditclear.paonet.di.module

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.lib.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.view.article.ArticleListFragment
import com.ditclear.paonet.view.code.CodeListFragment
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.Module
import dagger.Provides

/**
 * 页面描述：FragmentModule
 *
 * Created by ditclear on 2017/9/26.
 */
@Module
class FragmentModule(private val fragment: RxFragment) {

    @Provides
    fun provideFragment() = fragment

    @Provides
    fun provideContext(): Context =fragment.context

    @Provides
    fun provideFragmentManager(): FragmentManager = fragment.childFragmentManager

    @Provides
    fun provideHomePagerAdapter(): FragmentStatePagerAdapter {

        return object : AbstractPagerAdapter(fragment.childFragmentManager, arrayOf("Article", "Code")) {
            override fun getItem(pos: Int): Fragment? {
                when (pos) {
                    0 -> list[pos] = ArticleListFragment.newInstance()
                    1 -> list[pos] = CodeListFragment.newInstance()
                }
                return list[pos]
            }
        }
    }

//    @Provides @Named("collect")
//    fun provideMyCollectPagerAdapter(): FragmentStatePagerAdapter {
//
//        return object : AbstractPagerAdapter(fragment.childFragmentManager, arrayOf("文章", "代码")) {
//            override fun getItem(pos: Int): Fragment? {
//                when (pos) {
//                    0 -> list[pos] = MyArticleFragment.newInstance()
//                    1 -> list[pos] = MyArticleFragment.newInstance()
//                }
//                return list[pos]
//            }
//        }
//    }

}