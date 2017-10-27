package com.ditclear.paonet.viewmodel

import android.databinding.BaseObservable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 页面描述：StateModel 用于控制toast 和 各种空状态
 *
 * Created by ditclear on 2017/10/11.
 */
class StateModel : BaseObservable() {


    public fun bindError(e: Throwable) {
        if (e is SocketTimeoutException) {
        } else if (e is UnknownHostException || e is ConnectException) {
            //网络未连接

        }
    }
}