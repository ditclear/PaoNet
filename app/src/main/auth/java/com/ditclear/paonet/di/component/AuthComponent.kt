package com.ditclear.paonet.di.component

import com.ditclear.paonet.AuthService
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.di.module.AuthViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * 页面描述：MineComponent
 *
 * Created by ditclear on 2018/11/28.
 */
@Singleton
@Component(modules = [AppModule::class, AuthViewModelModule::class])
interface AuthComponent{

    fun inject(app: AuthService)


}