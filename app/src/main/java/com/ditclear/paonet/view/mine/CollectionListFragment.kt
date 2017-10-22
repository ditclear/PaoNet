package com.ditclear.paonet.view.mine

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.lib.extention.dpToPx
import com.ditclear.paonet.lib.extention.navigateToActivity
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.article.PagedAdapter
import com.ditclear.paonet.view.code.CodeDetailActivity
import com.ditclear.paonet.view.helper.navigateToArticleDetail
import com.ditclear.paonet.view.mine.viewmodel.MyCollectViewModel
import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject

/**
 * 页面描述：CollectionListFragment
 *
 * Created by ditclear on 2017/10/15.
 */
@FragmentScope
class CollectionListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<Article> {


    @Inject
    lateinit var viewModel: MyCollectViewModel

    lateinit var mAdapter : PagedAdapter<Article>

    var collectionType:Int=1

    override fun getLayoutId(): Int = R.layout.refresh_fragment

    companion object {

        private val COLLECTION_TYPE="type"

        fun newInstance(type:Int=1): CollectionListFragment {
            val bundle= Bundle()
            bundle.putInt(COLLECTION_TYPE,type)
            val fragment= CollectionListFragment()
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun lazyLoad() {
        if (!isPrepared || !visible||hasLoadOnce) {
            return
        }
        hasLoadOnce=true
        loadData(true)
    }


    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(isRefresh)
    }
    override fun initArgs(savedInstanceState: Bundle?) {
        collectionType=arguments.getInt(COLLECTION_TYPE,1)
    }

    @SingleClick
    override fun onItemClick(v: View?, t: Article) {
        if(collectionType==1) {
            navigateToArticleDetail(activity, v?.findViewById(R.id.thumbnail_iv), t)
        }else{
            activity.navigateToActivity(CodeDetailActivity::class.java, t)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)


    }

    override fun initView() {
        lazyLoad=true
        var layoutItemId=R.layout.article_list_item
        if (collectionType!=1){
            layoutItemId=R.layout.collect_code_list_item
        }
        mAdapter= PagedAdapter<Article>(activity, layoutItemId, viewModel.obserableList
                , object : DiffCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article)=
                    oldItem.id == newItem.id

            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                    oldItem.id == newItem.id

        })
        viewModel.lifecycle=bindToLifecycle<FragmentEvent>()
        viewModel.type=collectionType
        mBinding.vm=viewModel
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.addItemDecoration(object : DividerItemDecoration(activity, VERTICAL){
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.top=activity.dpToPx(R.dimen.xdp_12_0)
            }})
        mAdapter.presenter=this
        isPrepared=true
    }

}