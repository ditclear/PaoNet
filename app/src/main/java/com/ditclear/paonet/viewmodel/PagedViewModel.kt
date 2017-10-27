package com.ditclear.paonet.viewmodel

import android.databinding.ObservableBoolean

/**
 * 页面描述：PagedViewModel
 *
 * Created by ditclear on 2017/9/28.
 */
open class PagedViewModel : BaseViewModel() {

    val loading = ObservableBoolean(false)

    val loadMore = ObservableBoolean(false)

    var page = 0

    fun getPage(isRefresh: Boolean): Int {
        if (isRefresh) {
            page = 0
        } else {
            page++
        }
        return page
    }

    fun startLoad() {
        loading.set(true)
    }

    fun stopLoad() {
        loading.set(false)
    }

}