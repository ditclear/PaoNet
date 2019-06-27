package com.ditclear.paonet.view.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.ditclear.paonet.helper.extens.*
import com.ditclear.paonet.model.data.Category
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.viewmodel.BaseViewModel


/**
 * 页面描述：MainViewModel
 *
 * Created by ditclear on 2017/10/27.
 */

class MainViewModel(private val repo: PaoRepository) : BaseViewModel() {

    val user = MutableLiveData<User>().init(User())
    val categories = ObservableArrayList<CategoryItemViewModel>()

    val cateVisible = MutableLiveData<Boolean>().init(false)

    val face = MutableLiveData<String>()

    var qianming = MutableLiveData<String>()
    var navHeaderName = MutableLiveData<String>()
    var loginBtnText = MutableLiveData<String>().init("LOG IN")

    val exitEvent = ObservableBoolean(false)

    fun exit() = exitEvent.toFlowable().async(2000).doOnNext { exitEvent.set(false) }

    init {
        user.toFlowable()
                .doOnNext {
                    face.set(user.get()?.face)
                    qianming.set(user.get()?.qianming)
                    navHeaderName.set(user.get()?.nickname ?: "")
                    loginBtnText.set(if (user.get()?.nickname == null) "LOG IN" else "LOG OUT")
                }.subscribe()
    }


    //////////////////bind view/////////////
    /**
     * 获取代码分类
     */
    fun getCodeCategories() = repo.getCodeCategory().async()
            .map {
                categories.add(CategoryItemViewModel(Category("全部(All)")))
                categories.addAll(it.map { CategoryItemViewModel(it) })
            }

    fun toggleCategory() {
        val visible = cateVisible.get() ?: false
        cateVisible.set(!visible)
    }
}