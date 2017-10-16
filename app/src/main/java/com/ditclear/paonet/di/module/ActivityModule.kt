package com.ditclear.paonet.di.module

import com.ditclear.paonet.viewmodel.BaseViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.Module
import dagger.Provides

/**
 * 页面描述：app
 *
 * Created by ditclear on 2017/9/26.
 */
@Module
class ActivityModule(private val activity: RxAppCompatActivity){

    @Provides
    fun provideActivity():RxAppCompatActivity=activity


    @Provides
    fun provideViewModel():BaseViewModel= BaseViewModel()


}