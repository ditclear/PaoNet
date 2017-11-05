package com.ditclear.paonet.model.remote.api

import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.ArticleList
import com.ditclear.paonet.model.data.Category
import com.ditclear.paonet.model.data.TagList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 页面描述：PaoService
 *
 * Created by ditclear on 2017/9/29.
 */
interface PaoService {

    /**
     * 轮播图数据
     */
    @GET("slider.php")
    fun getSlider(): Single<ArticleList>

    /**
     * 获取代码分类
     */
    @GET("code_category_list.php")
    fun getCodeCategory():Single<List<Category>>

    /**
     * 文章列表
     */
    @GET("article_list.php")
    fun getArticleList(@Query("p") page: Int, @Query("tid") tid: Int?=null): Single<ArticleList>

    /**
     * 文章详情
     */
    @GET("article_detail.php")
    fun getArticleDetail(@Query("id") id: Int): Single<Article>

    /**
     * 代码列表
     */
    @GET("code_list.php")
    fun getCodeList(@Query("cate") category: Int? = null, @Query("p") page: Int): Single<ArticleList>

    /**
     * 代码详情
     */
    @GET("code_detail.php")
    fun getCodeDetail(@Query("id") id: Int): Single<Article>

    /**
     * 文章搜索
     * @param key 关键词
     * @param p 分页数
     */
    @GET("article_list.php")
    fun getSearchArticles( @Query("p") p: Int,@Query("key") key: String): Single<ArticleList>

    /**
     * 代码搜索
     * @param p 分页数
     * @param cate 代码分类
     * @param key 关键词
     */
    @GET("code_list.php")
    fun getSearchCode(@Query("p") p: Int, @Query("cate") cate: Int?=null, @Query("key") key: String): Single<ArticleList>

    /**
     * 获取热门搜索
     */
    @GET("hot_search.php")
    fun getHotSearch() : Single<TagList>
}
