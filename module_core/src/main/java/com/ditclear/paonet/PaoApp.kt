package com.ditclear.paonet

import android.app.Application
import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider
import es.dmoral.toasty.Toasty

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

object PaoApp {

    lateinit var component: AppComponent
        private set

    private var instance: Application? = null
    fun instance() = instance ?: throw Throwable("instance 还未初始化")


    fun onCreate(app: Application) {
        instance = app
        NetMgr.registerProvider(BaseNetProvider(app))
        SpUtil.init(app)
        component = DaggerAppComponent.builder().appModule(AppModule(app)).build()
        component.inject(app)

        Toasty.Config.getInstance().apply(); // required
    }
}
