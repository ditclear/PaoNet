package com.ditclear.paonet.view.home

import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.MainActivity
import com.ditclear.paonet.view.home.di.DaggerHomeComponent
import com.ditclear.paonet.view.home.di.HomeModule
import com.ditclear.paonet.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * 页面描述：首页
 *
 * Created by ditclear on 2017/9/30.
 */
class HomeFragment : BaseFragment<BaseViewModel,HomeFragmentBinding>(){

    @Inject
    lateinit var pagerAdapter:FragmentStatePagerAdapter

    val homeFragment by lazy { DaggerHomeComponent.builder().homeModule(HomeModule(childFragmentManager)).build() }

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun loadData(isRefresh: Boolean) {

    }

    override fun initArgs() {

        homeFragment.inject(this)
    }

    override fun initView() {
        (activity as MainActivity).setSupportActionBar(mBinding.toolbar)
        (activity as MainActivity).syncToolBar(mBinding.toolbar)
        mBinding.viewPager.adapter=pagerAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

    }



}