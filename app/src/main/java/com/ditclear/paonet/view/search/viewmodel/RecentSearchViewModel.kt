package com.ditclear.paonet.view.search.viewmodel

import android.databinding.ObservableArrayList
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.model.data.Tag
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.viewmodel.PagedViewModel
import javax.inject.Inject

/**
 * 页面描述：RecentSearchViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentSearchViewModel @Inject constructor(val repo: PaoRepository) : PagedViewModel() {

    val hotTags = ObservableArrayList<Tag>()
    val obserableList = ObservableArrayList<String>()


    fun loadData(isRefresh: Boolean) =
            repo.getHotSearch()
                    .async()
                    .map { t ->

                        t.items?.map { tag -> tag.keyword }
                    }
                    .doOnSuccess { t ->
                        if (isRefresh) {
                            obserableList.clear()
                        }
                        t?.run { obserableList.addAll(this) }
                    }


}