package com.ditclear.paonet.viewmodel

import android.databinding.ObservableField

/**
 * 页面描述：PagedViewModel
 *
 * Created by ditclear on 2017/9/28.
 */
open class PagedViewModel :BaseViewModel(){

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

    open fun loadData(isRefresh: Boolean){}

    fun stopLoad(){
        loading.set(false)
    }

}