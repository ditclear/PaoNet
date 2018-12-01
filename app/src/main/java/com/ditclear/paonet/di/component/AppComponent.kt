package com.ditclear.paonet.di.component

import com.ditclear.paonet.PaoApp
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * 页面描述：AppComponent
 *
 * Created by ditclear on 2017/9/29.
 */
@Singleton
@Component(modules = [(AppModule::class), (ViewModelModule::class)])
interface AppComponent {

    fun inject(app: PaoApp)

    @Component.Builder
    interface Builder {

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }
}