package com.ditclear.paonet.di.module

import android.app.Application
import com.ditclear.paonet.lib.network.NetMgr
import com.ditclear.paonet.model.remote.api.PaoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 页面描述：app
 *
 * Created by ditclear on 2017/9/26.
 */
@Module
class AppModule(val app:Application){

    @Provides @Singleton
    public fun provideApp() = app

    @Singleton
    @Provides
    fun provideRemoteClient(): Retrofit = NetMgr.getInstance().getRetrofit("http://api.jcodecraeer.com/");

    @Singleton
    @Provides
    fun providePaoService(): PaoService =provideRemoteClient().create(PaoService::class.java)
}