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
import com.ditclear.paonet.view.article.ArticleDetailActivity
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.MainActivity
import com.ditclear.paonet.view.mine.viewmodel.MyArticleViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 页面描述：MyArticleActivity
 *
 * Created by ditclear on 2017/10/15.
 */

class MyArticleFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel> {

    private val mVieModel: MyArticleViewModel  by viewModel()

    private val mAdapter: SingleTypeAdapter<ArticleItemViewModel> by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, R.layout.article_list_item, mVieModel.list).apply { itemPresenter = this@MyArticleFragment }
    }

    val showTab by lazy { autoWired(SHOW_TAB,false)?:false }

    override fun getLayoutId(): Int = R.layout.refresh_fragment

    companion object {

        private val SHOW_TAB = "show_tab"

        fun newInstance(showTab: Boolean = false): MyArticleFragment {
            val bundle = Bundle()
            bundle.putBoolean(SHOW_TAB, showTab)
            val fragment = MyArticleFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun loadData(isRefresh: Boolean) {
        mVieModel.loadData(isRefresh).bindLifeCycle(this)
                .subscribe ({},{toastFailure(it)})
    }

    @SingleClick
    override fun onItemClick(v: View?, item: ArticleItemViewModel) {
        activity?.navigateToActivity(ArticleDetailActivity::class.java, item.article)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)


    }

    override fun initView() {
        (activity as MainActivity).needShowTab(showTab)
        mBinding.vm=mVieModel
        mBinding.recyclerView.apply {
            this.adapter = mAdapter
            this.addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = activity?.dpToPx(R.dimen.xdp_12_0)?:0
                }
            })
        }
        show()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            show()
        }
    }

    fun show() {
        (activity as MainActivity).needShowTab(false)
    }

}