package com.ditclear.paonet

import android.arch.lifecycle.ViewModelProvider

/**
 * 页面描述：VMFactoryProivder
 *
 * Created by ditclear on 2018/12/3.
 */
interface FactoryProvider{
    fun getViewModelFactory():ViewModelProvider.Factory
}