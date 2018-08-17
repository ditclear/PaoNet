package com.ditclear.paonet.view.mine.viewmodel

import android.databinding.ObservableArrayList
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel
import javax.inject.Inject

/**
 * 页面描述：MyArticleViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class MyArticleViewModel @Inject constructor(private val repo: UserRepository) : PagedViewModel() {

    val obserableList = ObservableArrayList<ArticleItemViewModel>()

    fun loadData(isRefresh: Boolean) =
            repo.myArticle(getPage(isRefresh)).async(1000)
                    .doOnSuccess {
                        if (isRefresh) {
                            obserableList.clear()
                        }
                        loadMore.set(!it.incomplete_results)
                        it.items?.let {
                            obserableList.addAll(it.map { ArticleItemViewModel(it) })
                        }

                    }.doOnSubscribe { startLoad() }.doAfterTerminate {
                        stopLoad()
                        empty.set(obserableList.isEmpty())
                    }

}