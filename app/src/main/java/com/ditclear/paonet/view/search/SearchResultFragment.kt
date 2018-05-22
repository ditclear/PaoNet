package com.ditclear.paonet.view.search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.ContentMainBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.view.base.BaseFragment
import javax.inject.Inject
import javax.inject.Named

/**
 * 页面描述：SearchResultFragment
 *
 * Created by ditclear on 2017/10/24.
 */
class SearchResultFragment : BaseFragment<ContentMainBinding>() {

    override fun getLayoutId() = R.layout.content_main

    lateinit var keyWord: String

    @Inject
    @field:Named(Constants.Qualifier_SEARCH)
    lateinit var pagerAdapter: FragmentStatePagerAdapter

    companion object {
        private val KEY_KEYWORD = "keyWord"
        fun newInstance(keyWord: String): SearchResultFragment {

            return SearchResultFragment().apply {
                val bundle = Bundle()
                bundle.putString(KEY_KEYWORD, keyWord)
                arguments = bundle
            }
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun initArgs(savedInstanceState: Bundle?) {
        keyWord = arguments?.getString(KEY_KEYWORD)?:""
    }

    override fun initView() {
        getComponent().inject(this)
        mBinding.viewPager.adapter = pagerAdapter
        mBinding.viewPager.offscreenPageLimit = pagerAdapter.count
        (activity as SearchActivity).needShowTab(true)
        (activity as SearchActivity).setupWithViewPager(mBinding.viewPager)
    }

}