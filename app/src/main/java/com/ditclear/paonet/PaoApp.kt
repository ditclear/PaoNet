package com.ditclear.paonet

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import com.ditclear.paonet.di.appModule
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.network.NetMgr
import com.ditclear.paonet.model.remote.BaseNetProvider
import es.dmoral.toasty.Toasty
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger
import javax.inject.Inject

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

class PaoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NetMgr.registerProvider(BaseNetProvider(this))
        SpUtil.init(this)

        startKoin(this, appModule, logger = AndroidLogger(showDebug = true))

        Toasty.Config.getInstance().apply(); // required
    }
}
