package com.ditclear.paonet

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider
import com.ditclear.paonet.viewmodel.APPViewModelFactory
import es.dmoral.toasty.Toasty
import java.util.*
import javax.inject.Inject

/**
 * 页面描述：Application
 *
 * Created by ditclear on 2017/9/26.
 */

class PaoApp : Application(),FactoryProvider {

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        return factory
    }

    @Inject
    lateinit var factory: APPViewModelFactory

    val appModule by lazy { AppModule(this) }
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);

        NetMgr.registerProvider(BaseNetProvider(this))
        SpUtil.init(this)

        DaggerAppComponent.builder()
                .appModule(appModule)
                .build()
                .inject(this)

        Toasty.Config.getInstance().apply(); // required

        val loader = ServiceLoader.load(CoreService::class.java).iterator()
        while (loader.hasNext()) {
            val service = loader.next()
            Log.d("PaoNet-----", service.serviceName)
            service.init(this)
        }
    }
}
