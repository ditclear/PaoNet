package com.ditclear.paonet.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.article.viewmodel.ArticleListViewModel
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel
import com.ditclear.paonet.view.code.viewmodel.CodeListViewModel
import com.ditclear.paonet.view.home.viewmodel.MainViewModel
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import com.ditclear.paonet.viewmodel.APPViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * 页面描述：ViewModelModule
 *
 * Created by ditclear on 2018/8/17.
 */
@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(ArticleDetailViewModel::class)
    abstract fun bindArticleDetailViewModel(viewModel: ArticleDetailViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CodeDetailViewModel::class)
    abstract fun bindCodeDetailViewModel(viewModel: CodeDetailViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecentViewModel::class)
    abstract fun bindRecentViewModel(viewModel: RecentViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    abstract fun bindArticleListViewModel(viewModel: ArticleListViewModel):ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CodeListViewModel::class)
    abstract fun bindCodeListViewModel(viewModel: CodeListViewModel):ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory:APPViewModelFactory): ViewModelProvider.Factory

}