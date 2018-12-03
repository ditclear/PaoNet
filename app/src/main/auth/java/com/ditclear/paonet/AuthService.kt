package com.ditclear.paonet

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.ditclear.paonet.di.component.DaggerAuthComponent
import javax.inject.Inject
import javax.inject.Provider

/**
 * 页面描述：AuthService
 *
 * Created by ditclear on 2018/12/3.
 */
class AuthService : CoreService {

    @Inject
    lateinit var creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    override val serviceName: String
        get() = "auth"

    override fun init(app: Application) {

        check(app is PaoApp)

        DaggerAuthComponent.builder().appModule(app.appModule).build().inject(this)

        app.factory.plusMap(creators)
    }

}