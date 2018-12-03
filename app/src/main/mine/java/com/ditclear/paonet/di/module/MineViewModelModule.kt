package com.ditclear.paonet.di.module

import android.arch.lifecycle.ViewModel
import com.ditclear.paonet.view.mine.viewmodel.MyArticleViewModel
import com.ditclear.paonet.view.mine.viewmodel.MyCollectViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * 页面描述：MineViewModelModule
 *
 * Created by ditclear on 2018/11/28.
 */
@Module
abstract class MineViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(MyArticleViewModel::class)
    abstract fun bindMyArticleViewModel(viewModel: MyArticleViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyCollectViewModel::class)
    abstract fun bindMyCollectViewModel(viewModel: MyCollectViewModel):ViewModel

}