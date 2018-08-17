package com.ditclear.paonet.view.article

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.helper.adapter.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.dpToPx
import com.ditclear.paonet.helper.presenter.ListPresenter
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.article.viewmodel.ArticleListViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.viewmodel.StateModel

/**
 * 页面描述：ArticleListFragment
 *
 * Created by ditclear on 2017/10/3.
 */
class ArticleListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel>, ListPresenter {

    override val state: StateModel
        get() = viewModel.state

    private val viewModel: ArticleListViewModel by lazy {
        getInjectViewModel(ArticleListViewModel::class.java)
    }

    private val mAdapter by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, R.layout.article_list_item, viewModel.obserableList).apply {
            itemPresenter = this@ArticleListFragment
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
        viewModel.loadData(isRefresh).bindLifeCycle(this)
                .subscribe({},{
                    toastFailure(it)
                })
    }

    override fun initArgs(savedInstanceState: Bundle?) {
        arguments?.let {
            tid = it.getInt(KEY_TID)
            keyWord = it.getString(KEY_KEYWORD)
        }
    }


    override fun onItemClick(v: View?, item: ArticleItemViewModel) {

        activity?.let {
//        navigateToArticleDetail(it, v?.findViewById(R.id.thumbnail_iv), article = item.article)

            val intent = Intent(mContext,ArticleDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Constants.KEY_SERIALIZABLE, item.article)
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext as Activity)
            ActivityCompat.startActivity(mContext,intent,options.toBundle())


        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)

    }

    override fun initView() {
        lazyLoad = true
        viewModel.tid = tid
        viewModel.keyWord = keyWord
        mBinding.refreshLayout.setOnRefreshListener { loadData(true) }
        mBinding.run {

            vm = viewModel
            presenter = this@ArticleListFragment
            recyclerView.apply {
                adapter = mAdapter
                addItemDecoration(object : DividerItemDecoration(activity, DividerItemDecoration.VERTICAL) {
                    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        super.getItemOffsets(outRect, view, parent, state)
                        outRect?.top = activity?.dpToPx(R.dimen.xdp_12_0)
                    }
                })
            }
            isPrepared = true
        }

    }
}