package com.ditclear.paonet.view.home.viewmodel

import com.ditclear.paonet.model.data.ArticleList
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 页面描述：RecentViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentViewModel @Inject constructor(private val repo:PaoService) :BaseViewModel(){

    fun loadData(isRefresh : Boolean=true){

        repo.getSlider().compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .flatMap { repo.getArticleList(page = 0) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({t: ArticleList? ->  })
    }
}