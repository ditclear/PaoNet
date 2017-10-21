package com.ditclear.paonet.view.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.MainActivity
import javax.inject.Inject

/**
 * 页面描述：首页
 *
 * Created by ditclear on 2017/9/30.
 */
@FragmentScope
class HomeFragment : BaseFragment<HomeFragmentBinding>(){


    @Inject
    lateinit var pagerAdapter:FragmentStatePagerAdapter

    companion object {

        fun newInstance()= HomeFragment()
    }
    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun loadData(isRefresh: Boolean) {

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)

    }
    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {
        mBinding.viewPager.adapter=pagerAdapter
        mBinding.viewPager.offscreenPageLimit=pagerAdapter.count
        (activity as MainActivity).needShowTab(true)
        (activity as MainActivity).setupWithViewPager(mBinding.viewPager)
    }



}