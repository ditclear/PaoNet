package com.ditclear.paonet.di.module

import android.arch.lifecycle.ViewModel
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * 页面描述：MineViewModelModule
 *
 * Created by ditclear on 2018/11/28.
 */
@Module
abstract class AuthViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel):ViewModel

}