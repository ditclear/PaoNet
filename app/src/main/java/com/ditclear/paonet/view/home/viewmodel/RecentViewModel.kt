package com.ditclear.paonet.view.home.viewmodel

import androidx.databinding.ObservableArrayList
import android.util.Log
import com.ditclear.paonet.helper.adapter.recyclerview.Dummy
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.viewmodel.PagedViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * 页面描述：RecentViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentViewModel constructor(private val repo: PaoRepository) : PagedViewModel() {

    val sliders = ObservableArrayList<ArticleItemViewModel>()
    val list = ObservableArrayList<Any>()

    fun loadData(isRefresh: Boolean) =
            repo.getSlider()
                    .async()
                    .doOnSuccess {
                        if (isRefresh) {
                            sliders.clear()
                            list.clear()
                        }

                        it.items?.map { ArticleItemViewModel(it) }?.let {
                            list.add(Dummy())
                            sliders.addAll(it)
                        }

                    }
                    .observeOn(Schedulers.io())
                    .flatMap {
                        Log.d("thread------", Thread.currentThread().name)
                        repo.getArticleList(page = 0)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess {
                        Log.d("thread------", Thread.currentThread().name)
                        it.items?.map { ArticleItemViewModel(it) }?.let {
                            list.addAll(it)
                        }
                    }
                    .doOnSubscribe { startLoad() }
                    .doAfterTerminate {
                        stopLoad()
                        empty.set(list.isEmpty())
                    }


}