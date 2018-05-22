package com.ditclear.paonet.view.home

import android.content.Context
import android.databinding.ViewDataBinding
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.databinding.SliderBinding
import com.ditclear.paonet.helper.annotation.ItemType
import com.ditclear.paonet.helper.extens.dpToPx
import com.ditclear.paonet.helper.navigateToArticleDetail
import com.ditclear.paonet.helper.presenter.ListPresenter
import com.ditclear.paonet.lib.adapter.recyclerview.*
import com.ditclear.paonet.view.article.viewmodel.ArticleItemViewModel
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import com.ditclear.paonet.viewmodel.StateModel
import javax.inject.Inject

/**
 * 页面描述：RecentFragment
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentFragment : BaseFragment<RefreshFragmentBinding>(), ItemClickPresenter<Any>, ListPresenter {

    override val state: StateModel
        get() = viewModel.state


    override fun onItemClick(v: View?, t: Any) {
        if (t is ArticleItemViewModel) {
            activity?.let {
                navigateToArticleDetail(it, v, t.article)
            }
        }
    }

    @Inject
    lateinit var viewModel: RecentViewModel

    val sliderAdapter: PagedAdapter<ArticleItemViewModel> by lazy {
        PagedAdapter<ArticleItemViewModel>(mContext, R.layout.slider_item, viewModel.sliders).apply { itemPresenter = this@RecentFragment }
    }

    val mAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(mContext, viewModel.obserableList, object : MultiTypeAdapter.MultiViewTyper {
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
        getComponent().inject(this)
    }

    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(true).compose(bindToLifecycle())
                .subscribe({},{toastFailure(it)})


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
                                    outRect?.bottom = activity?.dpToPx(R.dimen.xdp_12_0)
                                }
                            }
                        }
                    }
                })
            }

        }

    }

}