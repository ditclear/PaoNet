package com.ditclear.paonet.model.data

import com.google.gson.annotations.SerializedName

/**
 * 页面描述：BaseResponse
 *
 * Created by ditclear on 2017/10/11.
 */
open class BaseResponse{
    /**
     * sucess : 1
     * message : 成功登录
     * data :
     */

    @SerializedName("sucess") var success: Int = 0
    var message: String? = null
    var data: String? = null
}