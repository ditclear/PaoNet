package com.ditclear.paonet.viewmodel

import android.databinding.BaseObservable
import com.ditclear.paonet.viewmodel.callback.ICallBack
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * 页面描述：StateModel 用于控制toast 和 各种空状态
 *
 * Created by ditclear on 2017/10/11.
 */
class StateModel : BaseObservable() {

    var mView: ICallBack? = null

    public fun attach(callBack: ICallBack) {
        this.mView = callBack
    }

    public fun detach() {
        this.mView = null
    }

    public fun bindError(e: Throwable) {
        if (e is SocketTimeoutException) {
            if (mView != null) {
                mView?.toastFailure(Throwable("网络连接超时"));
            }
        } else if (e is UnknownHostException || e is ConnectException) {
            //网络未连接
            mView?.toastFailure(Throwable("网络未连接"));

        }
    }
}