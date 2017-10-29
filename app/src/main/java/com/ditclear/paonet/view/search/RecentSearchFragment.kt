package com.ditclear.paonet.view.search

import android.content.Context
import android.databinding.ObservableList
import android.os.Bundle
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RecentSearchFragmentBinding
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.vendor.recyclerview.MultiTypeAdapter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.helper.ItemType
import com.ditclear.paonet.view.search.viewmodel.RecentSearchViewModel
import com.ditclear.paonet.widget.ColorBrewer
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import javax.inject.Inject

/**
 * 页面描述：RecentSearchFragment
 *
 * Created by ditclear on 2017/10/24.
 */
class RecentSearchFragment : BaseFragment<RecentSearchFragmentBinding>(), ItemClickPresenter<String> {

    override fun onItemClick(v: View?, item: String) {
        (activity as SearchActivity).setQuery(keyWord = item)
    }

    @Inject
    lateinit var viewModel: RecentSearchViewModel

    private val colorArray by lazy { ColorBrewer.Pastel2.getColorPalette(20) }

    companion object {
        fun newInstance() = RecentSearchFragment()
    }

    val adapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(mContext).apply {
            addViewTypeToLayoutMap(ItemType.HEADER, R.layout.recent_search_title_item)
            addViewTypeToLayoutMap(ItemType.ITEM, R.layout.recent_search_hot_item)
            addViewTypeToLayoutMap(ItemType.FOOTER, R.layout.recent_search_item)
            setDecorator { holder, position, _ ->
                if (position > 0) {
                    holder.binding.root.background.setTint(colorArray[position])
                }
            }
            setPresenter(this@RecentSearchFragment)
        }
    }

    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(true).compose(bindToLifecycle())
                .subscribe { _, t2 -> t2?.let { toastFailure(it) } }
        adapter.add(0, "热门搜索:", ItemType.HEADER)
    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    override fun initView() {
        mBinding.recyclerView.run {
            adapter = this@RecentSearchFragment.adapter
            layoutManager = FlexboxLayoutManager(mContext).apply {
                justifyContent = JustifyContent.SPACE_AROUND
            }
        }
        viewModel.obserableList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<String>>() {
            override fun onChanged(sender: ObservableList<String>?) {
            }

            override fun onItemRangeRemoved(sender: ObservableList<String>?, positionStart: Int, itemCount: Int) {
                adapter.clear()
            }

            override fun onItemRangeChanged(sender: ObservableList<String>?, positionStart: Int, itemCount: Int) {
            }

            override fun onItemRangeInserted(sender: ObservableList<String>?, positionStart: Int, itemCount: Int) {
                sender?.run {
                    adapter.addAll(sender, ItemType.ITEM)
                }
            }

            override fun onItemRangeMoved(sender: ObservableList<String>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    override fun getLayoutId(): Int = R.layout.recent_search_fragment

}