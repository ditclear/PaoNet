package com.ditclear.paonet.view.article.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.Utils
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.helper.extens.getOriginData
import com.ditclear.paonet.helper.extens.init
import com.ditclear.paonet.helper.extens.set
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
class ArticleDetailViewModel constructor(private val id: Int, private val repo: PaoRepository, private val userRepo: UserRepository) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>().init(true)

    val markdown = MutableLiveData<String>()

    val thumbnail = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val subTitle = MutableLiveData<String>()

    var article: Article? = null
    //加载详情
    fun loadData() = repo.getArticle(id).subscribeOn(Schedulers.io())
            .doOnSuccess {
                renderDetail(it)
            }.flatMap {
                if (SpUtil.user == null) {
                    return@flatMap Single.just(false)
                } else {
                    return@flatMap userRepo.isStow(id).getOriginData()
                            .map { t: BaseResponse? -> t?.data?.contentEquals("1") }
                }
            }.observeOn(AndroidSchedulers.mainThread()).doAfterTerminate { loading.set(false) }

    private fun renderDetail(article: Article?) {
        this.article = article
        article?.content?.let {
            val data = Utils.processImgSrc(it, Constants.HOST_PAO)
            markdown.set(data)
        }
        thumbnail.set(article?.thumbnail)
        nickname.set(article?.user?.nickname)
        subTitle.set(article?.title)
    }


    //关注
    fun attentionTo() = userRepo.followUser(id).getOriginData().async()


    //收藏
    fun stow() = userRepo.stow(id).getOriginData().async()


}
