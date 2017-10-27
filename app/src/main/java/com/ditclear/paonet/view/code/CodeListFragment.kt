package com.ditclear.paonet.view.code

import android.content.Context
import android.databinding.ObservableBoolean
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
import com.ditclear.paonet.model.remote.api.PaoService
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.article.PagedAdapter
import com.ditclear.paonet.view.code.viewmodel.CodeListViewModel
import com.ditclear.paonet.view.helper.ListPresenter
import javax.inject.Inject

/**
 * 页面描述：ArticleListFragment
 *
 * Created by ditclear on 2017/10/3.
 */
@FragmentScope
class CodeListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<Article>, ListPresenter {
    override val loadMore: ObservableBoolean
        get() = viewModel.loadMore

    @Inject
    lateinit var viewModel: CodeListViewModel

    @Inject
    lateinit var paoService: PaoService

    val mAdapter: PagedAdapter<Article> by lazy {
        PagedAdapter<Article>(activity, R.layout.code_list_item, viewModel.observableList
                , object : DiffCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

        }).apply {
            presenter = this@CodeListFragment
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

    override fun lazyLoad() {
        if (!isPrepared || !visible || hasLoadOnce) {
            return
        }
        hasLoadOnce = true
        loadData(true)
    }


    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(isRefresh).compose(bindToLifecycle())
                .doOnError { t: Throwable? -> t?.run { toastFailure(this) } }
                .subscribe()
    }

    override fun initArgs(savedInstanceState: Bundle?) {
        arguments?.let {
            cate = it.getInt(KEY_CATE)
            keyWord = it.getString(KEY_KEYWORD)

        }
    }

    @SingleClick
    override fun onItemClick(view: View?, code: Article) {
        activity.navigateToActivity(CodeDetailActivity::class.java, code)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)

    }

    override fun initView() {
        lazyLoad = true

        mBinding.run {

            vm = viewModel.apply {
                category = cate
                keyWord = keyWord
            }
            presenter=this@CodeListFragment
            recyclerView.apply {
                adapter = mAdapter
                addItemDecoration(object : DividerItemDecoration(activity, VERTICAL) {
                    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect?.top = activity.dpToPx(R.dimen.xdp_12_0)
                    }
                })

            }
        }
        isPrepared = true
    }

}