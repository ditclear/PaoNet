package com.ditclear.paonet.di.component

import com.ditclear.paonet.SearchService
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.di.module.SearchViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * 页面描述：SearchComponent
 *
 * Created by ditclear on 2018/11/28.
 */
@Singleton
@Component(modules = [AppModule::class,SearchViewModelModule::class])
interface SearchComponent{

    fun inject(app: SearchService)

}