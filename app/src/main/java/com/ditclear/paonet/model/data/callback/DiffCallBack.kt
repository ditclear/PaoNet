package com.ditclear.paonet.model.data.callback

/**
 * 页面描述：DiffCallBack 描述类是否相同
 *
 * Created by ditclear on 2017/9/28.
 */
interface DiffItemCallBack<T>{

    fun areItemsTheSame( newItem: T): Boolean

    fun areContentsTheSame(newItem: T): Boolean
}