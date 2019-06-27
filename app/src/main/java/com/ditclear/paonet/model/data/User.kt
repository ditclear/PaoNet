package com.ditclear.paonet.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * 页面描述：用户
 *
 * Created by ditclear on 2017/9/26.
 */

@Entity(tableName = "users")
class User :Serializable{
    /**
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
     */
    @PrimaryKey
    @ColumnInfo(name = "userid")
    var id: Int = 0

    var nickname: String? = null
    var face: String? = null
    var sex: String? = null
    var position: String? = null
    var city: String? = null
    var company: String? = null
    var education: String? = null
    var logindate: String? = null
    var signdate: String? = null
    var qianming: String? = null
    var fans: Int = 0
    var guanzhu: Int = 0


}
