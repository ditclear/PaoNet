package com.ditclear.paonet.view.search

import android.support.transition.Slide
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.ContentMainBinding
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.SinglePageActivity
import javax.inject.Inject
import javax.inject.Named

/**
 * 页面描述：SearchResultFragment
 *
 * Created by ditclear on 2017/10/24.
 */
class SearchResultFragment : BaseFragment<ContentMainBinding>() {

    override fun getLayoutId() = R.layout.content_main

    val keyWord by lazy { autoWired("keyWord", "") ?: "" }

    @Inject
    @field:Named(Constants.Qualifier_SEARCH)
    lateinit var pagerAdapter: FragmentStatePagerAdapter


    override fun loadData(isRefresh: Boolean) {

    }

    override fun initView() {
        getComponent().inject(this)
        exitTransition = Slide()
        mBinding.viewPager.adapter = pagerAdapter
        mBinding.viewPager.offscreenPageLimit = pagerAdapter.count
        (activity as SinglePageActivity?)?.getSearchFragment()?.run {
            needShowTab(true)
            setupWithViewPager(mBinding.viewPager)
        }
    }

}