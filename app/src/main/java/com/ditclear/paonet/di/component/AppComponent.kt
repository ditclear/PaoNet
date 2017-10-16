package com.ditclear.paonet.di.component

import android.app.Application
import com.ditclear.paonet.di.module.ActivityModule
import com.ditclear.paonet.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * 页面描述：AppComponent
 *
 * Created by ditclear on 2017/9/29.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent{


    fun inject(app: Application)

    fun plus(module: ActivityModule):ActivityComponent
}