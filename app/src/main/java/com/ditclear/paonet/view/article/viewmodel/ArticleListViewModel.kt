package com.ditclear.paonet.view.article.viewmodel

import androidx.databinding.ObservableArrayList
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.view.home.viewmodel.ArticleItemViewModelWrapper
import com.ditclear.paonet.viewmodel.PagedViewModel


/**
 * 页面描述：ArticleListViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class ArticleListViewModel
constructor(val tid: Int = ArticleType.HONGYANG, val keyWord: String? = null, private val repo: PaoRepository) : PagedViewModel() {

    val list = ObservableArrayList<ArticleItemViewModelWrapper>()


    fun loadData(isRefresh: Boolean) = if (keyWord != null) {
        repo.getSearchArticles(getPage(isRefresh), keyWord)
    } else {
        repo.getArticleList(getPage(isRefresh), tid)
    }
            .async(1000)
            .doOnSuccess {

                if (isRefresh) {
                    list.clear()
                }
                it.data?.let { response ->
                    loadMore.set(!response.over)
                    list.addAll(response.datas.map { ArticleItemViewModelWrapper(it) })
                }

            }.doOnSubscribe { startLoad() }.doAfterTerminate {
                stopLoad()
                empty.set(list.isEmpty())
            }


    private fun getPage(isRefresh: Boolean) = (if (isRefresh) 0 else list.size / 20) + 1
}