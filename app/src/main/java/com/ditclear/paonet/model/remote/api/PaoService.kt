package com.ditclear.paonet.model.remote.api

import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.ArticleList
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/29.
 */

open interface PaoService {

    @GET("article_list.php")
    fun getArticleList(@Query("p") page: Int): Flowable<ArticleList>


    @GET("article_detail.php")
    fun getArticleDetail(@Query("id") id:Int):Flowable<Article>
}
