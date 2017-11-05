package com.ditclear.paonet.view.ui.code.viewmodel

import android.app.Application
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.ditclear.paonet.BR
import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.view.helper.Constants
import com.ditclear.paonet.view.helper.SpUtil
import com.ditclear.paonet.view.helper.Utils
import com.ditclear.paonet.view.helper.extens.async
import com.ditclear.paonet.view.helper.extens.getOriginData
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
@ActivityScope
class CodeDetailViewModel @Inject
constructor( private val repo: PaoRepository, private val userRepo: UserRepository) : BaseViewModel() {

    val loading = ObservableBoolean(true)
    val markdown = ObservableField<String>()
    var article:Article ?=null
        @Bindable get
        set(value) {
            field = value
            notifyPropertyChanged(BR.article)
        }

    //加载详情
    fun loadData() = repo.getCodeDetail(article!!.id).subscribeOn(Schedulers.io())
            .doOnSuccess { t ->
                article=t
                val data = Utils.processImgSrc(t!!.readme!!, Constants.HOST_PAO)
                markdown.set(data)
                loading.set(false)
            }.flatMap {
        if (SpUtil.user == null) {
            return@flatMap Single.just(false)
        } else {
            return@flatMap userRepo.isStow(article!!.id).getOriginData()
                    .map { t: BaseResponse? -> t?.data?.contentEquals("1") }
        }
    }.observeOn(AndroidSchedulers.mainThread())


    //收藏
    fun stow() = userRepo.stow(article!!.id).getOriginData()
            .async()


    /////////////bind view//////////////

    fun getNameAndDate() = """${article!!.user?.nickname ?: "佚名"}
        |${article!!.pubDate}""".trimMargin()

}