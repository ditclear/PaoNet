package com.ditclear.paonet.viewmodel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import com.ditclear.paonet.model.remote.exception.EmptyException

/**
 * 页面描述：StateModel 用于控制toast 和 各种空状态
 *
 * Created by ditclear on 2017/10/11.
 */
class StateModel : BaseObservable() {

    val loading = ObservableBoolean(false)

    val loadMore = ObservableBoolean(false)

    val empty=ObservableBoolean(false)

    fun canLoadMore()=loadMore.get()&&!loading.get()

    fun showEmpty( emptyType:Int){
        if(!empty.get()) {
            empty.set(true)
        }
        throw EmptyException(emptyType)

    }

    fun hideEmpty(){
        if(empty.get()) {
            empty.set(false)
        }
    }
}