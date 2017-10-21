package com.ditclear.paonet

import android.app.Application
import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.lib.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider
import com.ditclear.paonet.view.helper.SpUtil
import es.dmoral.toasty.Toasty

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

class PaoApp : Application() {

    lateinit var component: AppComponent
        private set

    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
    }


    override fun onCreate() {
        super.onCreate()
        instance=this
        NetMgr.registerProvider(BaseNetProvider(this))
        SpUtil.init(this)
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)

        Toasty.Config.getInstance().apply(); // required
    }
}
