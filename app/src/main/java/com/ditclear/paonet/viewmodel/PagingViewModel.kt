package com.ditclear.paonet.viewmodel

import android.app.Application
import android.databinding.ObservableField
import com.ditclear.paonet.model.repository.PagingRepository

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/28.
 */
class PagingViewModel<repository:PagingRepository>(application: Application) :BaseViewModel(application ){

    val isLoading=ObservableField(false)

    init {

    }

}