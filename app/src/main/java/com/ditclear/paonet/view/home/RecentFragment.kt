package com.ditclear.paonet.view.home

import android.content.Context
import android.databinding.ObservableList
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.lib.extention.dpToPx
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.vendor.recyclerview.MultiTypeAdapter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.article.PagedAdapter
import com.ditclear.paonet.view.helper.ItemType
import com.ditclear.paonet.view.helper.navigateToArticleDetail
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import com.trello.rxlifecycle2.android.FragmentEvent
import javax.inject.Inject

/**
 * 页面描述：RecentFragment
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentFragment : BaseFragment<RefreshFragmentBinding>(), RecentViewModel.CallBack, ItemClickPresenter<Article> {
    override fun onItemClick(v: View?, t: Article) {
        navigateToArticleDetail(activity, v, t)
    }

    @Inject
    lateinit var viewModel: RecentViewModel

    val sliderAdapter: PagedAdapter<Article> by lazy {
        PagedAdapter<Article>(mContext, R.layout.slider_item, viewModel.sliders, object : DiffCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

        }).apply {
            presenter = this@RecentFragment
        }


    }


    val adapter: MultiTypeAdapter by lazy { MultiTypeAdapter(mContext) }

    override fun getLayoutId() = R.layout.refresh_fragment

    companion object {

        fun newInstance(): RecentFragment = RecentFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    override fun loadData(isRefresh: Boolean) {
        adapter.clear()
        adapter.add(0, sliderAdapter, ItemType.HEADER)
        viewModel.loadData(true)
    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {

        mBinding.vm = viewModel.apply {
            lifecycle = this@RecentFragment.bindToLifecycle<FragmentEvent>()
            attachView(this@RecentFragment)
        }

        mBinding.recyclerView.adapter = adapter.apply {
            addViewTypeToLayoutMap(ItemType.HEADER, R.layout.slider)
            addViewTypeToLayoutMap(ItemType.ITEM, R.layout.article_list_item)
            setPresenter(this@RecentFragment)
        }
        mBinding.recyclerView.addItemDecoration(object : DividerItemDecoration(activity, DividerItemDecoration.VERTICAL) {

            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent == null || view == null || state == null) {
                    return
                }
                val layoutManager = parent.layoutManager
                val lastPos = state.itemCount - 1
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
        viewModel.obserableList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Article>>() {
            override fun onItemRangeChanged(sender: ObservableList<Article>?, positionStart: Int, itemCount: Int) {
                sender?.let { adapter.notifyItemRangeChanged(1, positionStart, itemCount) }
            }

            override fun onChanged(sender: ObservableList<Article>?) {
                sender?.let { adapter.notifyItemRangeChanged(1, sender.size) }
            }

            override fun onItemRangeRemoved(sender: ObservableList<Article>?, positionStart: Int, itemCount: Int) {
                sender?.let { adapter.notifyItemRangeRemoved(positionStart + 1, itemCount) }
            }

            override fun onItemRangeMoved(sender: ObservableList<Article>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                adapter.notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableList<Article>?, positionStart: Int, itemCount: Int) {
                sender?.let { adapter.addAll(sender, ItemType.ITEM) }
            }

        })


    }

}