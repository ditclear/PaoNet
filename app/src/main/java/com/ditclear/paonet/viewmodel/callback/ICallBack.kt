package com.ditclear.paonet.viewmodel.callback

/**
 * 页面描述：view model回调
 *
 * Created by ditclear on 2017/9/28.
 */
interface ICallBack{

    fun toastFailure(error :Throwable)

    fun toastSuccess(msg:String?)
}