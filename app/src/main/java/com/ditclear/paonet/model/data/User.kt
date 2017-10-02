package com.ditclear.paonet.model.data

import java.io.Serializable

/**
 * 页面描述：用户
 *
 * Created by ditclear on 2017/9/26.
 */

class User :Serializable{
    var id: Int = 0
    var face: String? = null
    var nickname: String? = null
}
