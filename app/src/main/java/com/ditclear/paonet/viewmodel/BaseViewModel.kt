package com.ditclear.paonet.viewmodel

import android.arch.lifecycle.ViewModel

/**
 * 页面描述：viewModel 基类
 *
 * Created by ditclear on 2017/9/28.
 */

open class BaseViewModel() : ViewModel(){

    val state=StateModel()

}
