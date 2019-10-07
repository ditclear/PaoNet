package com.ditclear.paonet.model.data

data class PageResponse<T>(
        val curPage: Int,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int,
        val datas:List<T>
)