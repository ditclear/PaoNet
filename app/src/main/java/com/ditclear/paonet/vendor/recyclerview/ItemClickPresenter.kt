package com.ditclear.paonet.vendor.recyclerview

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/28.
 */
interface  ItemClickPresenter<Any> : BaseViewAdapter.Presenter{
    fun onItemClick(t:Any)
}