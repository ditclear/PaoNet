package com.ditclear.paonet.view.helper

import android.view.View
import com.ditclear.paonet.viewmodel.StateModel

/**
 * 页面描述：ListPresenter 列表页事件处理
 *
 * Created by ditclear on 2017/10/26.
 */
interface ListPresenter:View.OnClickListener{

    val state : StateModel

    fun loadData(isRefresh:Boolean)
}