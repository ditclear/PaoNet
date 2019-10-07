package com.ditclear.paonet.view.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
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

    val user = ObservableField<User>(User())
    val categories = ObservableArrayList<CategoryItemViewModelWrapper>()

    val cateVisible = ObservableBoolean(false)

    val face = ObservableField<String>()

    var qianming = ObservableField<String>()
    var navHeaderName = ObservableField<String>()
    var loginBtnText = ObservableField<String>("LOG IN")

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
            .doOnSuccess {
                it.data?.let {
                    categories.clear()
                    categories.addAll(it.map { item -> CategoryItemViewModelWrapper(item) })
                }
            }

    fun toggleCategory() {
        val visible = cateVisible.get() ?: false
        cateVisible.set(!visible)
    }
}