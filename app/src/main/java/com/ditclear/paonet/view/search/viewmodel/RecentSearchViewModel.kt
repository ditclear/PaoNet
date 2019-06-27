package com.ditclear.paonet.view.search.viewmodel

import androidx.databinding.ObservableArrayList
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.viewmodel.PagedViewModel


/**
 * 页面描述：RecentSearchViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentSearchViewModel constructor( val repo: PaoRepository) : PagedViewModel() {

    val list = ObservableArrayList<Any>()


    fun loadData(isRefresh: Boolean) =
            repo.getHotSearch()
                    .async()
                    .map { t ->
                        t.items?.map { tag -> tag.keyword }
                    }
                    .doOnSuccess { t ->
                        if (isRefresh) {
                            list.clear()
                        }
                        list.add("热门搜索:")
                        t?.let { list.addAll(it) }
                    }


}