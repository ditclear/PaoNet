package com.ditclear.paonet.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.RefreshFragmentBinding
import com.ditclear.paonet.model.data.callback.DiffItemCallBack
import com.ditclear.paonet.vendor.recyclerview.BaseViewAdapter
import com.ditclear.paonet.vendor.recyclerview.ItemClickPresenter
import com.ditclear.paonet.vendor.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.viewmodel.PagingViewModel

/**
 * 页面描述：通用刷新Fragment
 *
 * Created by ditclear on 2017/9/27.
 */
class RefreshFragment <T:DiffItemCallBack<T>> : BaseFragment<RefreshFragmentBinding>(){

    override fun loadData(isRefresh: Boolean) {

    }

    override fun getLayoutId() =R.layout.refresh_fragment

    private lateinit var mAdapter :SingleTypeAdapter<T>
    private var listItemId :Int ? =null
    private var presenter :BaseViewAdapter.Presenter ?=null

    companion object {
        private val KEY_LIST_ITEM_ID = "LIST_ITEM_ID"
        private val KEY_LIST_ITEM_PRESENTER = "LIST_ITEM_PRESENTER"

        fun <T:DiffItemCallBack<T>> newInstance(@LayoutRes listItemId: Int,presenter: ItemClickPresenter<T>): RefreshFragment<T> {
            val args = Bundle()
            args.putInt(KEY_LIST_ITEM_ID, listItemId)
            val fragment = RefreshFragment<T>()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initArgs() {
        listItemId=arguments?.getInt(KEY_LIST_ITEM_ID)
        presenter= arguments?.get(KEY_LIST_ITEM_PRESENTER) as BaseViewAdapter.Presenter?
    }

    override fun initView() {
        mAdapter= SingleTypeAdapter<T>(mContext,listItemId!!)
        mBinding.recyclerView.adapter=mAdapter
        mAdapter.setPresenter(presenter)
        val viewModel = ViewModelProviders.of(this).get(PagingViewModel::class.java)

    }

}