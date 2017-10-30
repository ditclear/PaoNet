package com.ditclear.paonet.view.home

import android.content.Context
import android.databinding.ObservableList
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.lib.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.lib.adapter.recyclerview.PagedAdapter
import com.ditclear.paonet.lib.extention.dpToPx
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.helper.ItemType
import com.ditclear.paonet.view.helper.ListPresenter
import com.ditclear.paonet.view.helper.navigateToArticleDetail
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import com.ditclear.paonet.viewmodel.StateModel
import com.ditclear.paonet.widget.recyclerview.MultiTypeAdapter
import javax.inject.Inject

/**
 * 页面描述：RecentFragment
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<ArticleItemViewModel>, ListPresenter {

    override val state: StateModel
        get() = viewModel.state


    override fun onItemClick(v: View?, t: ArticleItemViewModel) {
        navigateToArticleDetail(activity, v, t.article)
    }

    @Inject
    lateinit var viewModel: RecentViewModel

    val sliderAdapter: PagedAdapter<ArticleItemViewModel> by lazy {
        PagedAdapter<ArticleItemViewModel>(mContext, R.layout.slider_item, viewModel.sliders).apply { itemPresenter = this@RecentFragment }


    }


    val mAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(mContext)
                .apply {
                    addViewTypeToLayoutMap(ItemType.HEADER, R.layout.slider)
                    addViewTypeToLayoutMap(ItemType.ITEM, R.layout.article_list_item)
                    setPresenter(this@RecentFragment)
                }
    }

    override fun getLayoutId() = R.layout.refresh_fragment

    companion object {

        fun newInstance(): RecentFragment = RecentFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    override fun loadData(isRefresh: Boolean) {
        mAdapter.clear()
        mAdapter.add(0, sliderAdapter, ItemType.HEADER)
        viewModel.loadData(true).compose(bindToLifecycle())
                .subscribe { _, t2 -> t2?.let { toastFailure(it) } }


    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {

        mBinding.run {
            vm = viewModel
            presenter = this@RecentFragment

            recyclerView.run {
                adapter = mAdapter
                addItemDecoration(object : DividerItemDecoration(activity, DividerItemDecoration.VERTICAL) {

                    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                        super.getItemOffsets(outRect, view, parent, state)
                        if (parent == null || view == null || state == null) {
                            return
                        }
                        val layoutManager = parent.layoutManager
                        val current = parent.getChildLayoutPosition(view)
                        if (current == -1) {
                            return
                        }
                        if (layoutManager is LinearLayoutManager) {
                            if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                                if (current != 0) {
                                    outRect?.bottom = activity.dpToPx(R.dimen.xdp_12_0)
                                }
                            }
                        }
                    }
                })
            }

        }

        viewModel.obserableList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<ArticleItemViewModel>>() {
            override fun onItemRangeChanged(sender: ObservableList<ArticleItemViewModel>?, positionStart: Int, itemCount: Int) {
                sender?.let { mAdapter.notifyItemRangeChanged(1, positionStart, itemCount) }
            }

            override fun onChanged(sender: ObservableList<ArticleItemViewModel>?) {
                sender?.let { mAdapter.notifyItemRangeChanged(1, sender.size) }
            }

            override fun onItemRangeRemoved(sender: ObservableList<ArticleItemViewModel>?, positionStart: Int, itemCount: Int) {
                sender?.let { mAdapter.notifyItemRangeRemoved(positionStart + 1, itemCount) }
            }

            override fun onItemRangeMoved(sender: ObservableList<ArticleItemViewModel>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                mAdapter.notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableList<ArticleItemViewModel>?, positionStart: Int, itemCount: Int) {
                sender?.let { mAdapter.addAll(sender, ItemType.ITEM) }
            }

        })


    }

}