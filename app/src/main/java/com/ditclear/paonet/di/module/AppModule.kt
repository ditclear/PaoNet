package com.ditclear.paonet.di.module

import android.app.Application
import com.ditclear.paonet.lib.network.NetMgr
import com.ditclear.paonet.model.local.AppDatabase
import com.ditclear.paonet.model.local.dao.ArticleDao
import com.ditclear.paonet.model.local.dao.UserDao
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.model.remote.api.UserService
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.helper.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 页面描述：AppModule
 *
 * Created by ditclear on 2017/9/26.
 */
@Module
class AppModule(val app:Application){

    @Provides @Singleton
    fun provideApp() = app

    @Singleton
    @Provides
    fun provideRemoteClient(): Retrofit = NetMgr.getRetrofit(Constants.HOST_API);

    @Singleton
    @Provides
    fun providePaoService(): PaoService =provideRemoteClient().create(PaoService::class.java)

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit) :UserService =retrofit.create(UserService::class.java)

    @Singleton
    @Provides
    fun provideArticleDao(context:Application):ArticleDao = AppDatabase.getInstance(context).articleDao()

    @Singleton
    @Provides
    fun provideUserDao(context:Application):UserDao = AppDatabase.getInstance(context).userDao()

    @Singleton
    @Provides
    fun provideArticleRepo(remote:PaoService,local:ArticleDao)= PaoRepository(remote,local)


    @Singleton
    @Provides
    fun provideUserRepo(remote:UserService,local:UserDao)=UserRepository(remote,local)
}