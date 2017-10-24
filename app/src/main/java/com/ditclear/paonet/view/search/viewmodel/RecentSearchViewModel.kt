package com.ditclear.paonet.view.search.viewmodel

import android.databinding.ObservableArrayList
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.Tag
import com.ditclear.paonet.viewmodel.PagedViewModel
import javax.inject.Inject

/**
 * 页面描述：RecentSearchViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentSearchViewModel @Inject constructor() : PagedViewModel(){

    val hotTags = ObservableArrayList<Tag>()
    val obserableList = ObservableArrayList<Article>()


    override fun loadData(isRefresh: Boolean) {

    }

}