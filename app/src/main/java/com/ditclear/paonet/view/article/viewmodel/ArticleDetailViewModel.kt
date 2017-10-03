package com.ditclear.paonet.view.article.viewmodel

import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.viewmodel.BaseViewModel
import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
@ActivityScope
class ArticleDetailViewModel @Inject
constructor(lifecycle: LifecycleTransformer<Any>,private val repo: PaoService) : BaseViewModel(lifecycle) {

    fun loadData(articleId: Int): Flowable<Article> = repo.getArticleDetail(articleId)
}
