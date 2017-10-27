package com.ditclear.paonet.view.article.viewmodel

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.extention.getOriginData
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.model.remote.api.UserService
import com.ditclear.paonet.view.helper.Constants
import com.ditclear.paonet.view.helper.SpUtil
import com.ditclear.paonet.view.helper.Utils
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class ArticleDetailViewModel @Inject
constructor(private val repo: PaoService, private val userRepo: UserService) : BaseViewModel() {

    val loading = ObservableBoolean(true)

    val markdown = ObservableField<String>()

    lateinit var article: Article
        set

    //加载详情
    fun loadData() = repo.getArticleDetail(article.id).subscribeOn(Schedulers.io())
            .doOnSuccess { t ->
                val data = Utils.processImgSrc(t!!.content!!, Constants.HOST_PAO)
                markdown.set(data)
                loading.set(false)
            }.flatMap {
                if (SpUtil.user == null) {
                    return@flatMap Single.just(false)
                } else {
                    return@flatMap userRepo.isStow(article.id).getOriginData()
                            .map { t :BaseResponse?->t?.data?.contentEquals("1") }
                }
            }.observeOn(AndroidSchedulers.mainThread())






    //收藏
    fun stow() = userRepo.stow(article.id).getOriginData()
            .async()

    @CheckLogin
    @SingleClick
    fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> stow()
        }
    }


}
