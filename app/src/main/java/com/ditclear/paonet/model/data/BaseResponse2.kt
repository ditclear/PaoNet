package com.ditclear.paonet.model.data

import com.google.gson.annotations.SerializedName

/**
 * 页面描述：BaseResponse
 *
 * Created by ditclear on 2017/10/11.
 */
open class BaseResponse2<T>{
    /**
     * sucess : 1
     * message : 成功登录
     * data :
     */

    var errorCode: Int = 0
    var errorMsg: String? = null
    var data: T? = null
}