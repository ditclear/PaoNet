package com.ditclear.paonet.view.code.viewmodel

import android.databinding.ObservableField
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.di.scope.ActivityScope
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
import com.ditclear.paonet.viewmodel.callback.ICallBack
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
constructor(private val repo: PaoService,private val userRepo: UserService) : BaseViewModel() {

    lateinit var article:Article
        set

    val markdown = ObservableField<String>()

    //加载详情
    fun loadData() = repo.getCodeDetail(article.id).compose(bindToLifecycle()).subscribeOn(Schedulers.io())
            .doOnSuccess { t ->
                val data = Utils.processImgSrc(t!!.readme!!, Constants.HOST_PAO)
                markdown.set(data)
            }.flatMap {
                if (SpUtil.user == null) {
                    return@flatMap Single.just(false)
                } else {
                    return@flatMap userRepo.isStow(article.id).getOriginData()
                            .map { t : BaseResponse?->t?.data?.contentEquals("1") }
                }
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({t: Boolean? -> t?.let { mView.isStow(it) } },
                    {t: Throwable? ->t?.let { mView.toastFailure(it)  } })

    //收藏
    fun stow() =userRepo.stow(article.id).getOriginData().compose(bindToLifecycle())
            .async().subscribe({t -> mView.toastSuccess(t.message) }
            ,{t: Throwable? -> mView.toastFailure(t?:Exception("收藏失败")) })


    @SingleClick
    @CheckLogin
    fun onClick(view : View){
        when(view.id){
            R.id.fab -> stow()
        }
    }

    private lateinit var mView: CallBack

    fun attachView(v: CallBack) {
        this.mView = v
    }


    interface CallBack : ICallBack {

        fun isStow(stow:Boolean)
    }
}