package com.ditclear.paonet.viewmodel

/**
 * 页面描述：PagedViewModel
 *
 * Created by ditclear on 2017/9/28.
 */
open class PagedViewModel : BaseViewModel() {



    val loading = state.loading

    val loadMore = state.loadMore

    val empty=state.empty

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