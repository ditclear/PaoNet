package com.ditclear.paonet.view.mine.viewmodel

import androidx.databinding.ObservableArrayList
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel


/**
 * 页面描述：MyArticleViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class MyArticleViewModel constructor(private val repo: UserRepository) : PagedViewModel() {

    val list = ObservableArrayList<ArticleItemViewModel>()

    fun loadData(isRefresh: Boolean) =
            repo.myArticle(getPage(isRefresh)).async(1000)
                    .doOnSuccess {
                        if (isRefresh) {
                            list.clear()
                        }
                        loadMore.set(!it.incomplete_results)
                        it.items?.let {
                            list.addAll(it.map { ArticleItemViewModel(it) })
                        }

                    }.doOnSubscribe { startLoad() }.doAfterTerminate {
                        stopLoad()
                        empty.set(list.isEmpty())
                    }
    private fun getPage(isRefresh: Boolean)=if (isRefresh) 0 else list.size/20
}