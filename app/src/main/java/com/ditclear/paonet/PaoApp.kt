package com.ditclear.paonet

import android.app.Application

import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.lib.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider
import es.dmoral.toasty.Toasty

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
        NetMgr.registerProvider(BaseNetProvider(this))
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)

        Toasty.Config.getInstance()
                .setErrorColor(R.color.error_color) // optional
                .setInfoColor(R.color.colorPrimary) // optional
                .setSuccessColor(R.color.success_color) // optional
                .setWarningColor(R.color.warning_color) // optional
                .setTextColor(R.color.colorAccent) // optional
                .apply(); // required
    }
}
