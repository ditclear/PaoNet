package com.ditclear.paonet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableBoolean

/**
 * 页面描述：PagedViewModel
 *
 * Created by ditclear on 2017/9/28.
 */
open class PagedViewModel : ViewModel() {

    val loading = ObservableBoolean(true) //正在请求数据加载中...

    val loadMore = ObservableBoolean(false)   //是否可以上拉加载更多

    val empty=ObservableBoolean(false)   //是否展示空页面


    /**
     * 开始加载
     */
    fun startLoad() {
        loading.set(true)
    }

    /**
     * 停止加载
     */
    fun stopLoad() {
        loading.set(false)
    }

}