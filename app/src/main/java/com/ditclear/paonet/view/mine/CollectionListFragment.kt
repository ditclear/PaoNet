package com.ditclear.paonet.view.mine

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.helper.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.helper.adapter.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.dpToPx
import com.ditclear.paonet.helper.extens.navigateToActivity
import com.ditclear.paonet.helper.navigateToArticleDetail
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.code.CodeDetailActivity
import com.ditclear.paonet.view.mine.viewmodel.MyCollectViewModel

/**
 * 页面描述：CollectionListFragment
 *
 * Created by ditclear on 2017/10/15.
 */
@FragmentScope
class CollectionListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel> {

    private val viewModel: MyCollectViewModel  by lazy {
        getInjectViewModel(MyCollectViewModel::class.java)
    }

    val layoutItemId by lazy {
        if (collectionType != 1) {
            R.layout.collect_code_list_item
        } else R.layout.article_list_item
    }
    val mAdapter: SingleTypeAdapter<ArticleItemViewModel> by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, layoutItemId, viewModel.list).apply {
            itemPresenter = this@CollectionListFragment
        }
    }

    val collectionType by lazy { autoWired(COLLECTION_TYPE,1)?:1 }

    override fun getLayoutId(): Int = R.layout.refresh_fragment

    companion object {

        private val COLLECTION_TYPE = "type"

        fun newInstance(type: Int = 1): CollectionListFragment {
            val bundle = Bundle()
            bundle.putInt(COLLECTION_TYPE, type)
            val fragment = CollectionListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun lazyLoad() {
        if (!isPrepared || !visible || hasLoadOnce) {
            return
        }
        hasLoadOnce = true
        loadData(true)
    }


    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(isRefresh).bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })
    }

    @SingleClick
    override fun onItemClick(v: View?, item: ArticleItemViewModel) {
        activity?.let {
            if (collectionType == 1) {
                navigateToArticleDetail(it, v?.findViewById(R.id.thumbnail_iv), item.article)
            } else {
                it.navigateToActivity(CodeDetailActivity::class.java, item.article)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)


    }

    override fun initView() {
        lazyLoad = true

        mBinding.apply {

            vm = viewModel.apply {
                type = collectionType
            }
            recyclerView.adapter = mAdapter
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = activity?.dpToPx(R.dimen.xdp_12_0)?:0
                }
            })
        }

        isPrepared = true
    }

}