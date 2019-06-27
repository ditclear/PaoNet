package com.ditclear.paonet.view.mine

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.RefreshFragmentBinding
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
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 页面描述：CollectionListFragment
 *
 * Created by ditclear on 2017/10/15.
 */
class CollectionListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel> {

    private val collectionType by lazy { autoWired(COLLECTION_TYPE, 1) ?: 1 }


    private val mVieModel: MyCollectViewModel  by viewModel { parametersOf(collectionType) }

    val layoutItemId by lazy {
        if (collectionType != 1) {
            R.layout.collect_code_list_item
        } else R.layout.article_list_item
    }
    val mAdapter: SingleTypeAdapter<ArticleItemViewModel> by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, layoutItemId, mVieModel.list).apply {
            itemPresenter = this@CollectionListFragment
        }
    }


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
        mVieModel.loadData(isRefresh).bindLifeCycle(this)
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


    }

    override fun initView() {
        lazyLoad = true
        mBinding.vm = mVieModel
        mBinding.recyclerView.apply {
            this.adapter = mAdapter
            this.addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = activity?.dpToPx(R.dimen.xdp_12_0) ?: 0
                }
            })
        }

        isPrepared = true
    }

}