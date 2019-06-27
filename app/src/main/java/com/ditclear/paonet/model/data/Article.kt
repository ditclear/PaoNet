package com.ditclear.paonet.model.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 页面描述：文章
 *
 * Created by ditclear on 2017/9/26.
 */
@Entity(tableName = "articles")
class Article(var title: String?) : Serializable {

    @PrimaryKey
    @ColumnInfo(name = "articleid")
    var id: Int = 0
    @Embedded
    var user: User? = null
    var content: String? = null
    var readme: String? = null
    @SerializedName("describe")
    var description: String? = null
    var click: Int = 0
    var channel: Int = 0
    var comments: Int = 0
    var stow: Int = 0
    var upvote: Int = 0
    var downvote: Int = 0
    var url: String? = null
    var pubDate: String? = null
    var thumbnail: String? = null

}
