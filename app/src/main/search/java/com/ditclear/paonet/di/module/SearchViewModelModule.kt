package com.ditclear.paonet.di.module

import android.arch.lifecycle.ViewModel
import com.ditclear.paonet.view.search.viewmodel.RecentSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * 页面描述：SearchViewModelModule
 *
 * Created by ditclear on 2018/8/17.
 */
@Module
abstract class SearchViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(RecentSearchViewModel::class)
    abstract fun bindRecentSearchViewModel(viewModel: RecentSearchViewModel):ViewModel

}