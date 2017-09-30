package com.ditclear.paonet

import android.support.v7.widget.DividerItemDecoration
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.network.NetMgr
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.BaseNetProvider
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.vendor.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.view.BaseFragment
import javax.inject.Inject


class ContentFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<Article> {
    override fun loadData(isRefresh: Boolean) {
        mBinding.refreshLayout.isRefreshing = true
        mAdapter.clear()
        paoService.getArticleList(page++).compose(bindToLifecycle()).async()
                .subscribe({ t ->
                    with(t){
                        mAdapter.replace(items)
                        mBinding.recyclerView.scheduleLayoutAnimation()
                        mBinding.refreshLayout.isRefreshing=false
                    }

                })    }

    override fun initArgs() {

    }



    override fun getLayoutId(): Int = R.layout.refresh_fragment

    @Inject
    lateinit var paoService:PaoService

    private val component:AppComponent by lazy { DaggerAppComponent.builder().appModule(AppModule(activity.application)).build()}

    private val mAdapter :SingleTypeAdapter<Article> by lazy { SingleTypeAdapter<Article>(activity, R.layout.list_item) }
    override fun onItemClick(t: Article) {
        toast(t.title?:"hahah")
    }

    var page=0

    override fun initView() {
        NetMgr.registerProvider(BaseNetProvider())

        component.inject(this)

        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        mAdapter.setPresenter(this)
        mBinding.refreshLayout.setOnRefreshListener {
            loadData(true)
        }
    }
}

