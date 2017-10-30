package com.ditclear.paonet.view.home.viewmodel

import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.viewmodel.BaseViewModel

/**
 * 页面描述：MainViewModel
 *
 * Created by ditclear on 2017/10/27.
 */
@ActivityScope
class MainViewModel(val user: User) : BaseViewModel() {

    var face: String? = user.face
    var qianming: String? = user.qianming

    //////////////////bind view/////////////
    fun getNavHeaderName(): String = user.nickname ?: ""

    fun getLoginBtnText() = if (user.nickname == null) "LOG IN" else "LOG OUT"

}