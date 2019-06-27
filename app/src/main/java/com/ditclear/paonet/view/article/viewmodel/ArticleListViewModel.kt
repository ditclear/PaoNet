package com.ditclear.paonet.view.article.viewmodel

import androidx.databinding.ObservableArrayList
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.viewmodel.PagedViewModel


/**
 * 页面描述：ArticleListViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class ArticleListViewModel
constructor(val tid: Int = ArticleType.ANDROID, val keyWord: String? = null, private val repo: PaoRepository) : PagedViewModel() {

    val list = ObservableArrayList<ArticleItemViewModel>()


    fun loadData(isRefresh: Boolean) = if (keyWord != null) {
        repo.getSearchArticles(getPage(isRefresh), keyWord)
    } else {
        repo.getArticleList(getPage(isRefresh), tid)
    }
            .async(1000)
            .map { articleList ->
                with(articleList) {
                    loadMore.set(!incomplete_results)
                    if (isRefresh) {
                        list.clear()
                    }
                    return@map items?.map { ArticleItemViewModel(it) }?.let { list.addAll(it) }
                }
            }.doOnSubscribe { startLoad() }.doAfterTerminate {
                stopLoad()
                empty.set(list.isEmpty())
            }

    private fun getPage(isRefresh: Boolean) = if (isRefresh) 0 else list.size / 20
}