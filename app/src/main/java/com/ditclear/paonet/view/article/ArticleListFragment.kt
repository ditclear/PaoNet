package com.ditclear.paonet.view.article

import android.content.Context
import android.databinding.ObservableBoolean
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.lib.extention.dpToPx
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.article.viewmodel.ArticleListViewModel
import com.ditclear.paonet.view.helper.ArticleType
import com.ditclear.paonet.view.helper.ListPresenter
import com.ditclear.paonet.view.helper.navigateToArticleDetail
import javax.inject.Inject

/**
 * 页面描述：ArticleListFragment
 *
 * Created by ditclear on 2017/10/3.
 */
@FragmentScope
class ArticleListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<Article>,ListPresenter {
    override val loadMore: ObservableBoolean
        get() = viewModel.loadMore

    @Inject
    lateinit var viewModel: ArticleListViewModel

    val mAdapter: PagedAdapter<Article> by lazy {
        PagedAdapter<Article>(activity, R.layout.article_list_item, viewModel.obserableList
                , object : DiffCallback<Article>() {
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

        }).apply {
            presenter = this@ArticleListFragment
        }
    }

    var tid: Int = ArticleType.ANDROID

    var keyWord: String? = null

    override fun getLayoutId(): Int = R.layout.refresh_fragment

    companion object {
        val KEY_TID = "TID"
        val KEY_KEYWORD = "keyWord"
        fun newInstance(tid: Int = ArticleType.ANDROID): ArticleListFragment {

            val bundle = Bundle()
            bundle.putInt(KEY_TID, tid)
            val fragment = ArticleListFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(keyWord: String): ArticleListFragment {

            val bundle = Bundle()
            bundle.putString(KEY_KEYWORD, keyWord)
            val fragment = ArticleListFragment()
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
                .doOnError { t: Throwable? -> t?.let { toastFailure(it) } }
                .subscribe()
    }

    override fun initArgs(savedInstanceState: Bundle?) {
        arguments?.let {
            tid = arguments.getInt(KEY_TID)
            keyWord = arguments.getString(KEY_KEYWORD)
        }
    }


    override fun onItemClick(v: View?, t: Article) {
        navigateToArticleDetail(activity, v?.findViewById(R.id.thumbnail_iv), article = t)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)

    }

    override fun initView() {
        lazyLoad = true
        viewModel.tid = tid
        viewModel.keyWord = keyWord
        mBinding.run {
            vm = viewModel
            presenter=this@ArticleListFragment
            recyclerView.apply {
                adapter = mAdapter
                addItemDecoration(object : DividerItemDecoration(activity, DividerItemDecoration.VERTICAL) {
                    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect?.top = activity.dpToPx(R.dimen.xdp_12_0)
                    }
                })
            }
            isPrepared = true
        }

    }
}