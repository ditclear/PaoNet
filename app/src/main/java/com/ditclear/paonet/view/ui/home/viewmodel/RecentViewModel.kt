package com.ditclear.paonet.view.ui.home.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import com.ditclear.paonet.lib.adapter.recyclerview.Dummy
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.view.helper.extens.async
import com.ditclear.paonet.view.ui.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 页面描述：RecentViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentViewModel @Inject constructor( private val repo: PaoRepository) : PagedViewModel() {

    val sliders = ObservableArrayList<ArticleItemViewModel>()
    val obserableList = ObservableArrayList<Any>()

    fun loadData(isRefresh: Boolean) =
        repo.getSlider()
                .async()
                .doOnSuccess { t ->
                    if(isRefresh) {
                        sliders.clear()
                        obserableList.clear()
                    }
                    with(t) {
                        items?.map { ArticleItemViewModel(it) }?.let {
                            obserableList.add(Dummy())
                            sliders.addAll(it)
                        }
                    }
                }
                .observeOn(Schedulers.io())
                .flatMap {
                    Log.d("thread------",Thread.currentThread().name)
                    repo.getArticleList(page = 0) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoad() }
                .doFinally{ stopLoad()}
                .doOnSuccess{ articleList ->
                    Log.d("thread------",Thread.currentThread().name)
                    with(articleList) {
                        items?.map { ArticleItemViewModel(it) }?.let {
                            obserableList.addAll(it)
                        }
                    }
                }


}