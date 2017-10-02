package com.ditclear.paonet

import android.graphics.Rect
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.di.component.AppComponent
import com.ditclear.paonet.di.component.DaggerAppComponent
import com.ditclear.paonet.di.module.AppModule
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.extention.dpToPx
import com.ditclear.paonet.lib.extention.navigateToActivity
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.vendor.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.article.ArticleDetailActivity
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

    private val mAdapter :SingleTypeAdapter<Article> by lazy { SingleTypeAdapter<Article>(activity, R.layout.article_list_item) }
    override fun onItemClick(article: Article) {
        activity.navigateToActivity(ArticleDetailActivity::class.java,article)
    }

    var page=0

    override fun initView() {

        component.inject(this)

        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.addItemDecoration(object :DividerItemDecoration(activity, DividerItemDecoration.VERTICAL){
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.top=activity.dpToPx(R.dimen.xdp_12_0)
        }})

        mAdapter.setPresenter(this)
        mBinding.refreshLayout.setOnRefreshListener {
            loadData(true)
        }
    }
}

