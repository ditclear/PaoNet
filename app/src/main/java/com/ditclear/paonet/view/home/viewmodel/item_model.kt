package com.ditclear.paonet.view.home.viewmodel

import android.text.Html
import com.ditclear.paonet.model.data.Banner
import com.ditclear.paonet.model.data.WArticle

class BannerItemViewModel(data:Banner){

    val thumbnail = data.imagePath
    val title = data.title
    val url = data.url
}

class ArticleItemViewModelWrapper(val article: WArticle){

    val title =article.title
    val thumbnail  =article.envelopePic?:""
    val nickname = article.author ?: "佚名"
    val face :String?= null
    val content: String? = ""
    val readme: String? = ""
    val description= Html.fromHtml(article.desc)
    val click: Int = article.visible
    val channel: Int = 0
    val comments: Int = 0
    val stow: Int = 0
    val upvote: String = "${article.zan}"
    val downvote: Int = 0
    val url: String? = article.link
    val pubDate: String? = article.niceDate


    /**
     * @return as 2 days ago · 20 reads
     */
    val dateAndClicks = "$pubDate ·  $click reads"

    val codeDateAndClicks ="$click 查看  $stow 收藏\t$pubDate"

    val articleListResponses=if (comments<10) "$comments response" else "$comments responses"


}