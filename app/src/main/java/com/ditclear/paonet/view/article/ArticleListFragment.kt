package com.ditclear.paonet.view.article

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.helper.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.helper.adapter.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.dpToPx
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.article.viewmodel.ArticleListViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.viewmodel.ToTopOrRefreshContract
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * 页面描述：ArticleListFragment
 *
 * Created by ditclear on 2017/10/3.
 */
class ArticleListFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel>, ToTopOrRefreshContract {


    private val mAdapter by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, R.layout.article_list_item, mViewModel.list).apply {
            itemPresenter = this@ArticleListFragment
        }
    }

    private val tid by lazy { autoWired(KEY_TID, ArticleType.ANDROID) ?: ArticleType.ANDROID }

    private val keyWord by lazy { autoWired<String>(KEY_KEYWORD) }

    private val mViewModel: ArticleListViewModel by viewModel { parametersOf(tid, keyWord) }


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
        mViewModel.loadData(isRefresh).bindLifeCycle(this)

                .subscribe({

                }, {
                    toastFailure(it)
                })
    }

    override fun onItemClick(v: View?, item: ArticleItemViewModel) {

        activity?.let {
            val intent = Intent(mContext, ArticleDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Constants.KEY_SERIALIZABLE, item.article)
            intent.putExtras(bundle)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(it)
            ActivityCompat.startActivity(mContext, intent, options.toBundle())


        }
    }

    override fun initView() {
        lazyLoad = true
        mBinding.vm = mViewModel
        mBinding.recyclerView.apply {
            adapter = mAdapter
            addItemDecoration(object : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = activity?.dpToPx(R.dimen.xdp_12_0) ?: 0
                }
            })
        }
        isPrepared = true

    }

    override fun toTopOrRefresh() {
        if (mBinding.recyclerView.layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            val layoutManager = mBinding.recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
            if (layoutManager.findLastVisibleItemPosition() > 5) {
                mBinding.recyclerView.smoothScrollToPosition(0)
            } else {
                mBinding.recyclerView.smoothScrollToPosition(0)
                loadData(true)
            }
        }
    }
}