package com.ditclear.paonet.view.search

import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RecentSearchFragmentBinding
import com.ditclear.paonet.helper.adapter.recyclerview.BindingViewHolder
import com.ditclear.paonet.helper.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.helper.adapter.recyclerview.ItemDecorator
import com.ditclear.paonet.helper.adapter.recyclerview.MultiTypeAdapter
import com.ditclear.paonet.helper.annotation.ItemType
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.widget.ColorBrewer
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.search.viewmodel.RecentSearchViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * 页面描述：RecentSearchFragment
 *
 * Created by ditclear on 2017/10/24.
 */
class RecentSearchFragment : BaseFragment<RecentSearchFragmentBinding>(), ItemClickPresenter<Any> {

    override fun getLayoutId(): Int = R.layout.recent_search_fragment

    override fun onItemClick(v: View?, item: Any) {
        (activity as SearchActivity).setQuery(keyWord = item as String)
    }

    private val mVieModel: RecentSearchViewModel  by viewModel()

    private val colorArray by lazy { ColorBrewer.Pastel2.getColorPalette(20) }

    companion object {
        fun newInstance() = RecentSearchFragment()
    }

    private val adapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(mContext, mVieModel.list, object : MultiTypeAdapter.MultiViewTyper {
            override fun getViewType(item: Any): Int {
                val pos = mVieModel.list.indexOf(item)
                return if (pos == 0) ItemType.HEADER else ItemType.ITEM
            }

        }).apply {
            addViewTypeToLayoutMap(ItemType.HEADER, R.layout.recent_search_title_item)
            addViewTypeToLayoutMap(ItemType.ITEM, R.layout.recent_search_hot_item)
            addViewTypeToLayoutMap(ItemType.FOOTER, R.layout.recent_search_item)
            itemDecorator =
                    object : ItemDecorator {
                        override fun decorator(holder: BindingViewHolder<ViewDataBinding>?, position: Int, viewType: Int) {
                            if (position > 0) {
                                holder?.binding?.root?.background?.setTint(colorArray[position])
                            }
                        }

                    }


            itemPresenter = this@RecentSearchFragment
        }
    }

    override fun loadData(isRefresh: Boolean) {
        mVieModel.loadData(true).bindLifeCycle(this)
                .subscribe ({},{toastFailure(it)})
    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        mBinding.vm = mVieModel
        mBinding.recyclerView.run {
            adapter = this@RecentSearchFragment.adapter
            layoutManager = FlexboxLayoutManager(mContext).apply {
                justifyContent = JustifyContent.SPACE_AROUND
            }
        }

    }


}