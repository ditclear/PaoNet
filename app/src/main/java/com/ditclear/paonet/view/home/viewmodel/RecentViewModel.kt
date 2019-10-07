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

    val sliders = ObservableArrayList<BannerItemViewModel>()
    val list = ObservableArrayList<Any>()

    fun loadData(isRefresh: Boolean) =
            repo.getSlider()
                    .async()
                    .doOnSuccess {
                        if (isRefresh) {
                            sliders.clear()
                            list.clear()
                        }

                        it.data?.map { BannerItemViewModel(it) }?.let {
                            list.add(Dummy())
                            sliders.addAll(it)
                        }

                    }
                    .observeOn(Schedulers.io())
                    .flatMap {
                        repo.getTopList()
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess {
                        it.data?.map { ArticleItemViewModelWrapper(it) }?.let {
                            list.addAll(it)
                        }
                    }
                    .doOnSubscribe { startLoad() }
                    .doAfterTerminate {
                        stopLoad()
                        empty.set(list.isEmpty())
                    }


}