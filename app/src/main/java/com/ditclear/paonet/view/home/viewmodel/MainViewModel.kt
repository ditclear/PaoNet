package com.ditclear.paonet.view.home.viewmodel

import com.ditclear.paonet.di.scope.ActivityScope
import com.ditclear.paonet.model.remote.api.UserService
import com.ditclear.paonet.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * 页面描述：MainViewModel
 *
 * Created by ditclear on 2017/10/27.
 */
@ActivityScope
class MainViewModel @Inject constructor(val repo:UserService) :BaseViewModel(){

}