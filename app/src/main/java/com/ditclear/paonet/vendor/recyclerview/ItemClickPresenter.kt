package com.ditclear.paonet.vendor.recyclerview

import android.view.View

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/28.
 */
interface  ItemClickPresenter<Any> : BaseViewAdapter.Presenter{
    fun onItemClick(v: View?=null, item:Any)
}