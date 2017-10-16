package com.ditclear.paonet.view.article.viewmodel

import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.Single
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
@ActivityScope
class ArticleDetailViewModel @Inject
constructor(private val repo: PaoService) : BaseViewModel() {

    fun loadData(articleId: Int): Single<Article> = repo.getArticleDetail(articleId)
}
