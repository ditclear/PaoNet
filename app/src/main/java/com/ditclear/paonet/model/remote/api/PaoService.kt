package com.ditclear.paonet.model.remote.api

import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.ArticleList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 页面描述：PaoService
 *
 * Created by ditclear on 2017/9/29.
 */
 interface PaoService {

    @GET("article_list.php")
    fun getArticleList(@Query("p") page: Int): Single<ArticleList>


    @GET("article_detail.php")
    fun getArticleDetail(@Query("id") id:Int):Single<Article>

    @GET("code_list.php")
    fun getCodeList(@Query("cate") category: Int?=null,@Query("p") page: Int): Single<ArticleList>


    @GET("code_detail.php")
    fun getCodeDetail(@Query("id") id:Int):Single<Article>
}
