package com.ditclear.paonet.di.module

import com.ditclear.paonet.di.component.FragmentComponent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.Module
import dagger.Provides

/**
 * 页面描述：ActivityModule
 *
 * Created by ditclear on 2017/9/26.
 */
@Module(subcomponents = arrayOf(FragmentComponent::class))
class ActivityModule(private val activity: RxAppCompatActivity){

    @Provides
    fun provideActivity():RxAppCompatActivity=activity


}