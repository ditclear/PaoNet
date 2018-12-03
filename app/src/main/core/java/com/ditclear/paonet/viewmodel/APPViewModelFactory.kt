package com.ditclear.paonet.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * 页面描述：APPViewModelFactory  提供ViewModel 缓存的实例
 *
 * Created by ditclear on 2018/8/17.
 */
class APPViewModelFactory @Inject constructor(private val creators:Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory{
    private val innerMap = hashMapOf<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>().apply {
        putAll(creators)
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = innerMap[modelClass]?:innerMap.entries.firstOrNull{
            modelClass.isAssignableFrom(it.key)
        }?.value?:throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun plusMap(map:Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>){
        this.innerMap.putAll(map)
    }


}