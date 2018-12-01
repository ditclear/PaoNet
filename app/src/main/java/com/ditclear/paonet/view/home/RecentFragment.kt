package com.ditclear.paonet.view.home

import android.content.Context
import android.databinding.ViewDataBinding
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.databinding.SliderBinding
import com.ditclear.paonet.helper.adapter.recyclerview.*
import com.ditclear.paonet.helper.annotation.ItemType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.dpToPx
import com.ditclear.paonet.helper.navigateToArticleDetail
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import com.ditclear.paonet.view.home.viewmodel.ToTopOrRefreshContract

/**
 * 页面描述：RecentFragment
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<Any>, ToTopOrRefreshContract {


    override fun onItemClick(v: View?, t: Any) {
        if (t is ArticleItemViewModel) {
            activity?.let {
                navigateToArticleDetail(it, v, t.article)
            }
        }
    }

    private val viewModel: RecentViewModel by lazy {
        getInjectViewModel<RecentViewModel>()
    }

    val sliderAdapter: SingleTypeAdapter<ArticleItemViewModel> by lazy {
        SingleTypeAdapter<ArticleItemViewModel>(mContext, R.layout.slider_item, viewModel.sliders).apply { itemPresenter = this@RecentFragment }
    }

    val mAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(mContext, viewModel.list, object : MultiTypeAdapter.MultiViewTyper {
            override fun getViewType(item: Any): Int =
                    if (item is Dummy) ItemType.HEADER else ItemType.ITEM
        }).apply {
            addViewTypeToLayoutMap(ItemType.HEADER, R.layout.slider)
            addViewTypeToLayoutMap(ItemType.ITEM, R.layout.article_list_item)
            itemPresenter = this@RecentFragment
            itemDecorator = object : ItemDecorator {
                override fun decorator(holder: BindingViewHolder<ViewDataBinding>?, position: Int, viewType: Int) {
                    if (viewType == ItemType.HEADER && holder?.binding is SliderBinding) {
                        val binding = holder.binding as SliderBinding
                        if (binding.slider.adapter == null) {
                            binding.slider.adapter = sliderAdapter
                            binding.indicator.setViewPager(binding.slider)
                        }
                    }
                }

            }
        }
    }

    override fun getLayoutId() = R.layout.refresh_fragment

    companion object {

        fun newInstance(): RecentFragment = RecentFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(true).bindLifeCycle(this)
                .subscribe({}, { toastFailure(it) })


    }

    override fun initView() {

        mBinding.vm=viewModel
        mBinding.recyclerView.apply {
            adapter = mAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {

                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val layoutManager = parent.layoutManager
                    val current = parent.getChildLayoutPosition(view)
                    if (current == -1) {
                        return
                    }
                    if (layoutManager is LinearLayoutManager) {
                        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
                            if (current != 0) {
                                outRect.bottom = activity?.dpToPx(R.dimen.xdp_12_0) ?: 0
                            }
                        }
                    }
                }
            })
        }


    }

    override fun toTopOrRefresh() {
        if (mBinding.recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = mBinding.recyclerView.layoutManager as LinearLayoutManager
            if (layoutManager.findLastVisibleItemPosition() > 5) {
                mBinding.recyclerView.smoothScrollToPosition(0)
            } else {
                mBinding.recyclerView.smoothScrollToPosition(0)
                loadData(true)
            }
        }
    }

}