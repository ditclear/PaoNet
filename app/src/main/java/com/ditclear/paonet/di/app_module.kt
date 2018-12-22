package com.ditclear.paonet.di

import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.network.NetMgr
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.local.AppDatabase
import com.ditclear.paonet.model.local.dao.ArticleDao
import com.ditclear.paonet.model.local.dao.UserDao
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.model.remote.api.UserService
import com.ditclear.paonet.model.repository.PaoRepository
import com.ditclear.paonet.model.repository.UserRepository
import com.ditclear.paonet.view.article.viewmodel.ArticleDetailViewModel
import com.ditclear.paonet.view.article.viewmodel.ArticleListViewModel
import com.ditclear.paonet.view.auth.viewmodel.LoginViewModel
import com.ditclear.paonet.view.code.viewmodel.CodeDetailViewModel
import com.ditclear.paonet.view.code.viewmodel.CodeListViewModel
import com.ditclear.paonet.view.home.viewmodel.MainViewModel
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import com.ditclear.paonet.view.mine.viewmodel.MyArticleViewModel
import com.ditclear.paonet.view.mine.viewmodel.MyCollectViewModel
import com.ditclear.paonet.view.search.viewmodel.RecentSearchViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.create
import org.koin.experimental.builder.getForClass
import retrofit2.Retrofit

val viewModelModule = module {

    viewModel { (article: Article) -> ArticleDetailViewModel(article, get(), get()) }
    viewModel { (article: Article, nameDate: String) -> CodeDetailViewModel(article, nameDate, get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { RecentViewModel(get()) }
    viewModel { (tid: Int, keyWord: String?) -> ArticleListViewModel(tid, keyWord, get()) }
    viewModel { (category: Int?, keyWord: String?) -> CodeListViewModel(category, keyWord, get()) }
    viewModel { RecentSearchViewModel(get()) }
    viewModel { MyArticleViewModel(get()) }
    viewModel { (type: Int) -> MyCollectViewModel(type, get()) }
    viewModel { LoginViewModel(get()) }

}

val remoteModule = module {

    single<Retrofit> { NetMgr.getRetrofit(Constants.HOST_API) }
    single<PaoService> { get<Retrofit>().create(PaoService::class.java) }
    single<UserService> { get<Retrofit>().create(UserService::class.java) }

}

val localModule = module {

    single<AppDatabase> { AppDatabase.getInstance(androidApplication()) }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<ArticleDao> { get<AppDatabase>().articleDao() }

}

val repoModule = module {

    single { PaoRepository(get(), get()) }
    single { UserRepository(get(), get()) }

}


val appModule = listOf(viewModelModule, remoteModule, localModule, repoModule)
