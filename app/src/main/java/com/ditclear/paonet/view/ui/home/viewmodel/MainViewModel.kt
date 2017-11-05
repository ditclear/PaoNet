package com.ditclear.paonet.view.ui.home.viewmodel

import android.databinding.*
import com.android.databinding.library.baseAdapters.BR
import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.model.data.Category
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.view.helper.extens.async
import com.ditclear.paonet.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * 页面描述：MainViewModel
 *
 * Created by ditclear on 2017/10/27.
 */
@ActivityScope
class MainViewModel @Inject constructor(val repo: PaoService) : BaseViewModel() {

    val user = ObservableField<User>(User())
    val categories = ObservableArrayList<CategoryItemViewModel>()
    val cateVisible=ObservableBoolean(false)

    var face: String? = user.get().face
        @Bindable get
    var qianming: String? = user.get().qianming
        @Bindable get

    init {
        user.addOnPropertyChangedCallback(object :Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                notifyChange()
                notifyPropertyChanged(BR.face)
                notifyPropertyChanged(BR.qianming)
            }

        })
    }


    //////////////////bind view/////////////
    @Bindable
    fun getNavHeaderName(): String = user.get().nickname ?: ""


    @Bindable()
    fun getLoginBtnText() = if (user.get().nickname == null) "LOG IN" else "LOG OUT"

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