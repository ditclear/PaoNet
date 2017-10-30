package com.ditclear.paonet.view.code.viewmodel

import android.databinding.ObservableArrayList
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel
import javax.inject.Inject

/**
 * 页面描述：ArticleListViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
@FragmentScope
class CodeListViewModel
@Inject
constructor(private val repo: PaoRepository) : PagedViewModel() {

    val observableList = ObservableArrayList<ArticleItemViewModel>()

    //null代表全部
    var category: Int? = null
        set
    var keyWord: String? = null
        set

    fun loadData(isRefresh: Boolean)=
            if (keyWord != null) {
                repo.getSearchCode(getPage(isRefresh), key = keyWord!!)
            } else {
                repo.getCodeList(category, getPage(isRefresh))
            }.async(1000)
                    .map { articleList ->
                        with(articleList) {
                            if (isRefresh) {
                                observableList.clear()
                                if (items==null|| items.isEmpty()){
                                    state.showEmpty(1)
                                }else {
                                    state.hideEmpty()
                                }
                            }
                            loadMore.set(!incomplete_results)
                            return@map items?.map { ArticleItemViewModel(it) }?.let { observableList.addAll(it) }
                        }
                    }.doOnSubscribe { startLoad() }.doAfterTerminate { stopLoad() }!!


}