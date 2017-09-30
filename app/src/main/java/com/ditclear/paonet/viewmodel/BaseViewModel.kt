package com.ditclear.paonet.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

/**
 * 页面描述：viewModel 基类
 *
 * Created by ditclear on 2017/9/28.
 */

open class BaseViewModel(application: Application) : AndroidViewModel(application){


    fun loadData(isRefresh :Boolean){

    }
}
