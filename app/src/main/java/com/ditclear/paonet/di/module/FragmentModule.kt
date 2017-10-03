package com.ditclear.paonet.di.module

import com.ditclear.paonet.model.data.Article
import com.trello.rxlifecycle2.components.support.RxFragment
import dagger.Module
import dagger.Provides

/**
 * 页面描述：fragment
 *
 * Created by ditclear on 2017/9/26.
 */
@Module
class FragmentModule(val fragment: RxFragment) {

    @Provides
    fun provideFragment() = fragment

    @Provides
    fun  provideLifeCycle() = fragment.bindToLifecycle<Article>()
}