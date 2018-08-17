package com.ditclear.paonet.view.article.viewmodel

import android.databinding.ObservableArrayList
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.viewmodel.PagedViewModel
import javax.inject.Inject

/**
 * 页面描述：ArticleListViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class ArticleListViewModel
@Inject
constructor(private val repo: PaoRepository) : PagedViewModel() {

    val obserableList = ObservableArrayList<ArticleItemViewModel>()

    var tid = ArticleType.ANDROID
        set

    var keyWord: String? = null
        set

    fun loadData(isRefresh: Boolean) = if (keyWord != null) {
        repo.getSearchArticles(getPage(isRefresh), keyWord ?: "")
    } else {
        repo.getArticleList(getPage(isRefresh), tid)
    }
            .async(1000)
            .map { articleList ->
                with(articleList) {
                    loadMore.set(!incomplete_results)
                    if (isRefresh) {
                        obserableList.clear()
                        if (items == null || items.isEmpty()) {
                            state.showEmpty(1)
                        } else {
                            state.hideEmpty()
                        }
                    }
                    return@map items?.map { ArticleItemViewModel(it) }?.let { obserableList.addAll(it) }
                }
            }.doOnSubscribe { startLoad() }.doAfterTerminate {
                stopLoad()
                empty.set(obserableList.isEmpty())
            }

}