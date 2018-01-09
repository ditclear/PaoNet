package com.ditclear.paonet.viewmodel

import com.ditclear.paonet.helper.annotation.EmptyState

/**
 * 页面描述：PagedViewModel
 *
 * Created by ditclear on 2017/9/28.
 */
open class PagedViewModel() : BaseViewModel() {



    val loading = state.loading //正在请求数据加载中...

    val loadMore = state.loadMore   //是否可以上拉加载更多

    val empty=state.empty   //是否展示空页面

    var page = 0

    fun getPage(isRefresh: Boolean): Int {
        if (isRefresh) {
            page = 0
        } else {
            page++
        }
        return page
    }

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

    /**
     * 显示空页面
     * @param type 空页面类型
     */
    fun showEmpty(@EmptyState type:Int){
        state.showEmpty(type)
    }

    /**
     * 隐藏空页面
     */
    fun hideEmpty(){
        state.hideEmpty()
    }

}