package com.ditclear.paonet

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider
import es.dmoral.toasty.Toasty
import javax.inject.Inject

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

class PaoApp : Application() {


    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        NetMgr.registerProvider(BaseNetProvider(this))
        SpUtil.init(this)

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)

        Toasty.Config.getInstance().apply(); // required
    }
}
