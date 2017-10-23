package com.ditclear.paonet.di.module

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.lib.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.view.article.ArticleListFragment
import com.ditclear.paonet.view.helper.ArticleType
import com.ditclear.paonet.view.home.RecentFragment
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

        return object : AbstractPagerAdapter(fragment.childFragmentManager, arrayOf( "Recent","ANDROID","程序设计","前端开发","IOS","数据库","开发日志","应用推荐","每日一站")) {
            override fun getItem(pos: Int): Fragment? {
                if (list[pos]==null) {
                    when (pos) {
                        0 -> list[pos] = RecentFragment.newInstance()
                        1 -> list[pos] = ArticleListFragment.newInstance(ArticleType.ANDROID)
                        2 -> list[pos] = ArticleListFragment.newInstance(ArticleType.PROGRAME)
                        3 -> list[pos] = ArticleListFragment.newInstance(ArticleType.FRONT_END)
                        4 -> list[pos] = ArticleListFragment.newInstance(ArticleType.IOS)
                        5 -> list[pos] = ArticleListFragment.newInstance(ArticleType.DB)
                        6 -> list[pos] = ArticleListFragment.newInstance(ArticleType.DEVLOG)
                        7 -> list[pos] = ArticleListFragment.newInstance(ArticleType.RECOMMAND)
                        8 -> list[pos] = ArticleListFragment.newInstance(ArticleType.DAILY)
                    }
                }
                return list[pos]
            }
        }
    }
}