package com.ditclear.paonet.model.data

/**
 * 页面描述：Category 代码类型
 *
 * Created by ditclear on 2017/10/24.
 */

data class Category(var catename: String?, var value: Int?=null, var count: Int?=null) {
    /*{
  "catename": "提示 (tip)",
  "value": 500,
  "count": 0
    }*/

}

data class WCategory(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)