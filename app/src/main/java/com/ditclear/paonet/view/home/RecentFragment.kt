package com.ditclear.paonet.view.home

import android.os.Bundle
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.vendor.recyclerview.MultiTypeAdapter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.helper.ItemType
import com.ditclear.paonet.view.home.viewmodel.RecentViewModel
import javax.inject.Inject

/**
 * 页面描述：RecentFragment
 *
 * Created by ditclear on 2017/10/22.
 */
class RecentFragment : BaseFragment<RefreshFragmentBinding>(){

    @Inject
    lateinit var viewModel:RecentViewModel

    val adapter :MultiTypeAdapter by lazy { MultiTypeAdapter(mContext) }

    override fun getLayoutId()= R.layout.refresh_fragment

    companion object {

        fun newInstance():RecentFragment = RecentFragment()
    }

    override fun loadData(isRefresh: Boolean) {
        viewModel.loadData(true)
    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        adapter.apply {
            addViewTypeToLayoutMap(ItemType.HEADER,R.layout.slider)
            addViewTypeToLayoutMap(ItemType.ITEM,R.layout.article_list_item)

        }

        mBinding.recyclerView.adapter=adapter

    }

}