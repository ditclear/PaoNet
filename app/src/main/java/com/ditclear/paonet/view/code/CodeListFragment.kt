package com.ditclear.paonet.view.code

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
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.code.viewmodel.CodeListViewModel
import com.ditclear.paonet.view.home.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 页面描述：ArticleListFragment
 *
 * Created by ditclear on 2017/10/3.
 */
class CodeListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel> {



    private val mAdapter: SingleTypeAdapter<ArticleItemViewModel> by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, R.layout.code_list_item, mViewModel.list).apply {
            itemPresenter = this@CodeListFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.refresh_fragment

    val cate by lazy { autoWired<Int>(KEY_CATE) }

    val keyWord by lazy { autoWired<String>(KEY_KEYWORD) }

    private val mViewModel: CodeListViewModel by viewModel { parametersOf(cate,keyWord)}


    companion object {

        val KEY_CATE = "cate"
        val KEY_KEYWORD = "keyWord"
        fun newInstance(cate: Int?): CodeListFragment {

            val bundle = Bundle()
            cate?.let { bundle.putInt(KEY_CATE, it) }
            val fragment = CodeListFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(keyWord: String): CodeListFragment {

            val bundle = Bundle()
            bundle.putString(KEY_KEYWORD, keyWord)
            val fragment = CodeListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun loadData(isRefresh: Boolean) {
        mViewModel.loadData(isRefresh).bindLifeCycle(this)
                .subscribe({},{toastFailure(it)})
    }

    @SingleClick
    override fun onItemClick(v: View?, item: ArticleItemViewModel) {
        activity?.navigateToActivity(CodeDetailActivity::class.java, item.article)
    }

    override fun initView() {
        mBinding.vm=mViewModel
        mBinding.run {
            recyclerView.apply {
                adapter = mAdapter
                addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect.top = activity?.dpToPx(R.dimen.xdp_12_0)?:0
                    }
                })

            }

            refreshLayout.setOnRefreshListener { loadData(true) }
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
        (activity as? MainActivity)?.needShowTab(false)
    }

}