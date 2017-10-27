package com.ditclear.paonet.view.helper

import android.databinding.ObservableBoolean
import android.view.View

/**
 * 页面描述：ListPresenter 列表页事件处理
 *
 * Created by ditclear on 2017/10/26.
 */
interface ListPresenter:View.OnClickListener{

    val loadMore : ObservableBoolean

    fun loadData(isRefresh:Boolean)
}