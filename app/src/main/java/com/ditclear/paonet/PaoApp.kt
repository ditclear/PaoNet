package com.ditclear.paonet

import android.app.Application

import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.lib.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

class PaoApp : Application() {

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        NetMgr.registerProvider(BaseNetProvider())
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }
}
