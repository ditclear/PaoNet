package com.ditclear.paonet.view.ui.mine.viewmodel

import android.databinding.ObservableArrayList
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.view.helper.extens.async
import com.ditclear.paonet.view.ui.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel
import javax.inject.Inject

/**
 * 页面描述：MyArticleViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
@FragmentScope
class MyArticleViewModel
@Inject
constructor(private val repo: UserRepository) : PagedViewModel() {

    val obserableList = ObservableArrayList<ArticleItemViewModel>()

    fun loadData(isRefresh: Boolean) =
            repo.myArticle(getPage(isRefresh)).async(1000)
                    .map { articleList ->
                        with(articleList) {
                            if (isRefresh) {
                                obserableList.clear()
                            }
                            loadMore.set(!incomplete_results)
                            return@map items?.map { ArticleItemViewModel(it) }?.let { obserableList.addAll(it) }
                        }
                    }.doOnSubscribe { startLoad() }.doFinally { stopLoad() }!!

}