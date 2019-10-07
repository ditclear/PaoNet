package com.ditclear.paonet.model.remote.api

import com.ditclear.paonet.model.data.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
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
    @GET("banner/json")
    fun getSlider(): Single<BaseResponse2<List<Banner>>>

    @GET("article/top/json")
    fun getTopList() : Single<BaseResponse2<List<WArticle>>>

    /**
     * 获取代码分类
     */
    @GET("project/tree/json")
    fun getCodeCategory():Single<BaseResponse2<List<WCategory>>>

    /**
     * 文章列表
     */
    @GET("wxarticle/list/{tid}/{page}/json")
    fun getArticleList(@Path("tid") tid:Int,@Path("page") page: Int): Single<BaseResponse2<PageResponse<WArticle>>>

    /**
     * 文章详情
     */
    @GET("article_detail.php")
    fun getArticleDetail(@Query("id") id: Int): Single<Article>

    /**
     * 代码列表
     */
    @GET("project/list/{p}/json")
    fun getCodeList( @Path("p") page: Int,@Query("cid") category: Int? = null): Single<BaseResponse2<PageResponse<WArticle>>>

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
    @POST("article/query/{p}/json")
    fun getSearchArticles(@Path("p") p: Int, @Query("k") key: String): Single<BaseResponse2<PageResponse<WArticle>>>

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
    @GET("hotkey/json")
    fun getHotSearch() : Single<BaseResponse2<List<Tag>>>
}
