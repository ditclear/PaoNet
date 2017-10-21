package com.ditclear.paonet.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 页面描述：用户
 *
 * Created by ditclear on 2017/9/26.
 */

class UserModel : Serializable {
    /**
     * {
     * "model":{
     * id : 14161
     * nickname : fxx
     * face : http://www.jcodecraeer.com/plugin/Identicon/index.php?string=fxx&size=60
     * sex : 男
     * position :
     * city :
     * company :
     * education :
     * logindate : 2017-08-14 23:31:03
     * signdate : 2017-08-14 08:50:08
     * qianming : 还木有签名哦
     * fans : 0
     * guanzhu : 0
     * }
     * }
     */
    @SerializedName("model")
    var model:User?=null
}