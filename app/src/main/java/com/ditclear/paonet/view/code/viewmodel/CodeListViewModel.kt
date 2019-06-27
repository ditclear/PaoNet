package com.ditclear.paonet.view.code.viewmodel

import androidx.databinding.ObservableArrayList
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel


/**
 * 页面描述：CodeListViewModel
 * @param category null代表全部
 *
 * Created by ditclear on 2017/10/3.
 */
class CodeListViewModel constructor(private val category: Int?=null,private val keyWord: String? = null, private val repo: PaoRepository) : PagedViewModel() {

    val list = ObservableArrayList<ArticleItemViewModel>()



    fun loadData(isRefresh: Boolean) = if (keyWord != null) {
        repo.getSearchCode(getPage(isRefresh), key = keyWord ?: "")
    } else {
        repo.getCodeList(category, getPage(isRefresh))
    }.async(1000)
            .map { articleList ->
                with(articleList) {
                    if (isRefresh) {
                        list.clear()
                    }
                    loadMore.set(!incomplete_results)
                    return@map items?.map { ArticleItemViewModel(it) }?.let { list.addAll(it) }
                }
            }.doOnSubscribe { startLoad() }.doAfterTerminate {
                stopLoad()
                empty.set(list.isEmpty())
            }

    private fun getPage(isRefresh: Boolean)=if (isRefresh) 0 else list.size/20
}