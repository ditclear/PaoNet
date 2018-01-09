package com.ditclear.paonet.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import com.ditclear.paonet.BR
import com.ditclear.paonet.PaoApp
import com.ditclear.paonet.R
import com.ditclear.paonet.model.remote.exception.EmptyException
import com.ditclear.paonet.helper.annotation.EmptyState

/**
 * 页面描述：StateModel 用于控制toast 和 各种空状态
 *
 * Created by ditclear on 2017/10/11.
 */
class StateModel : BaseObservable() {

    val app=PaoApp.instance()

    val loading = ObservableBoolean(false)

    val loadMore = ObservableBoolean(false)

    val empty=ObservableBoolean(false)

    fun canLoadMore()=loadMore.get()&&!loading.get()

    var emptyState =EmptyState.NORMAL
        @Bindable get
        set(@EmptyState value) {
            field = value
            notifyPropertyChanged(BR.currentStateLabel)
        }

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

    /**
     * 获取空状态
     */
    @Bindable
    fun getCurrentStateLabel(): String {

        var resId = R.string.no_data

        when(emptyState) {
        }
        return app.getString(resId)
    }
}