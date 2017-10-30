package com.ditclear.paonet.model.repository

import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.ArticleList
import com.ditclear.paonet.model.data.TagList
import com.ditclear.paonet.model.local.dao.ArticleDao
import com.ditclear.paonet.model.remote.api.PaoService
import io.reactivex.Single
import javax.inject.Inject

/**
 * 页面描述：PaoRepository
 *
 * Created by ditclear on 2017/10/30.
 */
class PaoRepository @Inject constructor(val remote: PaoService, val local: ArticleDao) {



    /**
     * 轮播图数据
     */
    fun getSlider(): Single<ArticleList> = remote.getSlider()

    /**
     * 文章列表
     */
    fun getArticleList(page: Int, tid: Int? = null): Single<ArticleList> = remote.getArticleList(page, tid)


    /**
     * 文章详情
     */
    fun getArticle(articleId: Int): Single<Article> = remote.getArticleDetail(articleId)
            .doOnSuccess { t -> t?.let { local.insertArticle(it) } }
            .onErrorResumeNext { local.getArticleById(articleId) }

    /**
     * 代码列表
     */
    fun getCodeList(category: Int? = null, page: Int): Single<ArticleList> = remote.getCodeList(category, page)

    /**
     * 代码详情
     */
    fun getCodeDetail(id: Int): Single<Article> = remote.getCodeDetail(id)
            .doOnSuccess { t: Article? -> t?.let { local.insertArticle(it) } }
            .onErrorResumeNext { local.getArticleById(id) }

    /**
     * 文章搜索
     * @param key 关键词
     * @param p 分页数
     */
    fun getSearchArticles(p: Int, key: String): Single<ArticleList> = remote.getSearchArticles(p, key)

    /**
     * 代码搜索
     * @param p 分页数
     * @param cate 代码分类
     * @param key 关键词
     */
    fun getSearchCode(p: Int, cate: Int? = null, key: String): Single<ArticleList> = remote.getSearchCode(p, cate, key)

    /**
     * 获取热门搜索
     */
    fun getHotSearch(): Single<TagList> = remote.getHotSearch()
}