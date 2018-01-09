package com.ditclear.paonet.view.article.viewmodel

import com.ditclear.paonet.model.data.Article

/**
 * 页面描述：ArticleItemViewModel
 *
 * Created by ditclear on 2017/10/27.
 */
class ArticleItemViewModel(val article:Article){

    val title =article.title
    val thumbnail  =article.thumbnail?:""
    val nickname = article.user?.nickname ?: "佚名"
    val face :String?= article.user?.face
    val content: String? = article.content
    val readme: String? = article.readme
    val description: String? = article.description
    val click: Int = article.click
    val channel: Int = article.channel
    val comments: Int = article.comments
    val stow: Int = article.stow
    val upvote: String = "${article.upvote}"
    val downvote: Int = article.downvote
    val url: String? = article.url
    val pubDate: String? = article.pubDate


    /**
     * @return as 2 days ago · 20 reads
     */
    val dateAndClicks = "$pubDate ·  $click reads"

    val codeDateAndClicks ="$click 查看  $stow 收藏\t$pubDate"

    val articleListResponses=if (comments<10) "$comments response" else "$comments responses"

}