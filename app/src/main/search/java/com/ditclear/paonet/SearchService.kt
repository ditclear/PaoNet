package com.ditclear.paonet

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.ditclear.paonet.di.component.DaggerSearchComponent
import javax.inject.Inject
import javax.inject.Provider

/**
 * 页面描述：SearchService
 *
 * Created by ditclear on 2018/11/28.
 */
class SearchService :CoreService{

    @Inject
    lateinit var creators:Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    override fun init(app: Application) {
        check(app is PaoApp)
        DaggerSearchComponent.builder().appModule(app.appModule).build().inject(this)
        app.factory.plusMap(creators)
    }

    override val serviceName: String = "search"

}