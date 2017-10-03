package com.ditclear.paonet.viewmodel

import android.databinding.ObservableField
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * 页面描述：PagedViewModel
 *
 * Created by ditclear on 2017/9/28.
 */
open class PagedViewModel(lifecycle: LifecycleTransformer<*>) :BaseViewModel(lifecycle){

    val loading=ObservableField(false)

    val loadMore=ObservableField(false)

    var page =0

    fun startLoad(isRefresh :Boolean){
        if (isRefresh){
            page=0
        }else{
            page++
        }

        loading.set(true)
    }

    fun stopLoad(){
        loading.set(false)
    }

}