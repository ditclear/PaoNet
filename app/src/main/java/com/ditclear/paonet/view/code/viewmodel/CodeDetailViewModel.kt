package com.ditclear.paonet.view.code.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.Utils
import com.ditclear.paonet.helper.extens.*
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.viewmodel.BaseViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * 页面描述：ArticleDetailViewModel
 *
 * Created by ditclear on 2017/10/3.
 */
class CodeDetailViewModel
constructor(code: Article, nameDate:String,private val repo: PaoRepository, private val userRepo: UserRepository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>().init(true)
    val markdown = MutableLiveData<String>()
    val article = MutableLiveData<Article>().init(code)
    val nameAndDate = MutableLiveData<String>().init(nameDate)


    //加载详情
    fun loadData() = Single.create<Int> { emitter ->
        article.get()?.let {
            emitter.onSuccess(it.id)
        } ?: emitter.onError(Throwable("无效的id"))
    }.flatMap { repo.getCodeDetail(it) }.subscribeOn(Schedulers.io())
            .doOnSuccess {
                article.set(it)
                it?.readme?.let {
                    val data = Utils.processImgSrc(it, Constants.HOST_PAO)
                    markdown.set(data)
                }
                loading.set(false)
            }.flatMap {
                //判断是否已经收藏
                if (SpUtil.user == null) {
                    return@flatMap Single.just(false)
                } else {
                    return@flatMap userRepo.isStow(it.id)
                            .getOriginData()
                            .map { t: BaseResponse? -> t?.data?.contentEquals("1") }
                }
            }.observeOn(AndroidSchedulers.mainThread())


    //收藏
    fun stow() = Single.create<Int> { emitter ->
        article.get()?.let {
            emitter.onSuccess(it.id)
        } ?: emitter.onError(Throwable("无效的id"))
    }.flatMap { userRepo.stow(it) }.getOriginData().async()


}