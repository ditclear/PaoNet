package com.ditclear.paonet.view.code

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.helper.extens.dpToPx
import com.ditclear.paonet.helper.extens.navigateToActivity
import com.ditclear.paonet.helper.presenter.ListPresenter
import com.ditclear.paonet.lib.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.lib.adapter.recyclerview.PagedAdapter
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.code.viewmodel.CodeListViewModel
import com.ditclear.paonet.view.home.MainActivity
import com.ditclear.paonet.viewmodel.StateModel
import javax.inject.Inject

/**
 * 页面描述：ArticleListFragment
 *
 * Created by ditclear on 2017/10/3.
 */
@FragmentScope
class CodeListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel>, ListPresenter {
    override val state: StateModel
        get() = viewModel.state


    @Inject
    lateinit var viewModel: CodeListViewModel


    private val mAdapter: PagedAdapter<ArticleItemViewModel> by lazy {
        PagedAdapter<ArticleItemViewModel>(mContext, R.layout.code_list_item, viewModel.observableList).apply {
            itemPresenter = this@CodeListFragment
        }
    }

    override fun getLayoutId(): Int = R.layout.refresh_fragment

    var cate: Int? = null

    var keyWord: String? = null

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
        viewModel.loadData(isRefresh).compose(bindToLifecycle())
                .subscribe({},{toastFailure(it)})
    }

    override fun initArgs(savedInstanceState: Bundle?) {
        arguments?.let {
            cate = it.getInt(KEY_CATE)
            keyWord = it.getString(KEY_KEYWORD)

        }
    }

    @SingleClick
    override fun onItemClick(v: View?, item: ArticleItemViewModel) {
        activity?.navigateToActivity(CodeDetailActivity::class.java, item.article)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)

    }

    override fun initView() {
        mBinding.run {

            vm = viewModel.apply {
                category = cate
                keyWord = this@CodeListFragment.keyWord
            }
            presenter=this@CodeListFragment
            recyclerView.apply {
                adapter = mAdapter
                addItemDecoration(object : DividerItemDecoration(activity, VERTICAL) {
                    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect?.top = activity?.dpToPx(R.dimen.xdp_12_0)
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