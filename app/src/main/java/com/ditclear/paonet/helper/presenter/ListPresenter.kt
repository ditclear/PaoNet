package com.ditclear.paonet.helper.presenter

import android.view.View
import com.ditclear.paonet.viewmodel.StateModel

/**
 * 页面描述：ListPresenter 列表页事件处理
 *
 * Created by ditclear on 2017/10/26.
 */
interface ListPresenter:Presenter{

    val state : StateModel

    fun loadData(isRefresh:Boolean)

    override fun onClick(v: View?)
}