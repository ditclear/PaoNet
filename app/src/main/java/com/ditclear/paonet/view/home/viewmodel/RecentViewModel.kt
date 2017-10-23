package com.ditclear.paonet.view.home.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.viewmodel.PagedViewModel
import com.ditclear.paonet.viewmodel.callback.ICallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 页面描述：RecentViewModel
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentViewModel @Inject constructor(private val repo: PaoService) : PagedViewModel() {

    val sliders = ObservableArrayList<Article>()
    val obserableList = ObservableArrayList<Article>()

    override fun loadData(isRefresh: Boolean) {
        startLoad(true)
        repo.getSlider().compose(bindToLifecycle())
                .async()
                .doOnSuccess { t ->
                    Log.d("thread------",Thread.currentThread().name)
                    sliders.clear()
                    with(t) {
                        items?.let { sliders.addAll(it) }
                    }
                }
                .observeOn(Schedulers.io())
                .flatMap {
                    Log.d("thread------",Thread.currentThread().name)
                    repo.getArticleList(page = 0) }
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate{ stopLoad()}
                .subscribe({ articleList ->
                    Log.d("thread------",Thread.currentThread().name)
                    with(articleList) {
                        if (isRefresh) {
                            obserableList.clear()
                        }
                        items?.let { obserableList.addAll(it) }
                    }
                }, { t: Throwable? -> t?.let { mView.toastFailure(t) } })
    }

    private lateinit var mView: CallBack

    fun attachView(v: CallBack) {
        this.mView = v
    }


    interface CallBack : ICallBack {

    }
}