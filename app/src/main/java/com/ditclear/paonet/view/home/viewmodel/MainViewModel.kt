package com.ditclear.paonet.view.home.viewmodel

import android.databinding.Observable
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.model.data.Category
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * 页面描述：MainViewModel
 *
 * Created by ditclear on 2017/10/27.
 */

class MainViewModel @Inject constructor(val repo: PaoService) : BaseViewModel() {

    val user = ObservableField<User>(User())
    val categories = ObservableArrayList<CategoryItemViewModel>()
    val cateVisible=ObservableBoolean(false)

    val face = ObservableField<String>()

    var qianming = ObservableField<String>()
    var navHeaderName = ObservableField<String>()
    var loginBtnText = ObservableField<String>("LOG IN")

    init {
        user.addOnPropertyChangedCallback(object :Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                face.set(user.get()?.face)
                qianming.set(user.get()?.qianming)
                navHeaderName.set(user.get()?.nickname ?: "")
                loginBtnText.set( if (user.get()?.nickname == null) "LOG IN" else "LOG OUT")
            }

        })
    }


    //////////////////bind view/////////////
    /**
     * 获取代码分类
     */
    fun getCodeCategories() = repo.getCodeCategory().async()
            .map {
                categories.add(CategoryItemViewModel(Category("全部(All)")))
                categories.addAll(it.map { CategoryItemViewModel(it) }) }

    fun toggleCategory(){
        cateVisible.set(!cateVisible.get())
    }
}