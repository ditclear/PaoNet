package com.ditclear.paonet.view.article.viewmodel

import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.extention.getOriginData
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.model.remote.api.UserService
import com.ditclear.paonet.viewmodel.BaseViewModel
import com.ditclear.paonet.viewmodel.callback.ICallBack
import io.reactivex.Single
import javax.inject.Inject

/**
 * 页面描述：ArticleDetailViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
@ActivityScope
class ArticleDetailViewModel @Inject
constructor(private val repo: PaoService,private val userRepo:UserService) : BaseViewModel() {

    lateinit var article:Article
        set

    fun loadData(): Single<Article> = repo.getArticleDetail(article.id)

    //收藏
    fun stow() =userRepo.stow(article.id).getOriginData().compose(bindToLifecycle())
            .async().subscribe({t -> mView.toastSuccess(t.message) }
            ,{t: Throwable? -> mView.toastFailure(t?:Exception("收藏失败")) })

    @CheckLogin
    @SingleClick
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

    }
}
