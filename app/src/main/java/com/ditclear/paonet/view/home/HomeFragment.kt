package com.ditclear.paonet.view.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.helper.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.helper.annotation.ArticleType
import com.ditclear.paonet.view.article.ArticleListFragment
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.viewmodel.ToTopOrRefreshContract

/**
 * 页面描述：首页
 *
 * Created by ditclear on 2017/9/30.
 */

class HomeFragment : BaseFragment<HomeFragmentBinding>() {


    private val pagerAdapter: androidx.fragment.app.FragmentStatePagerAdapter by lazy {
        object : AbstractPagerAdapter(childFragmentManager, arrayOf("Recent", "鸿洋", "郭霖", "玉刚说", "承香墨影", "Android群英传", "谷歌开发者", "美团技术团队", "Gityuan")) {
            override fun getItem(pos: Int): androidx.fragment.app.Fragment? {
                if (list[pos] == null) {
                    when (pos) {
                        0 -> list[pos] = RecentFragment.newInstance()
                        1 -> list[pos] = ArticleListFragment.newInstance(ArticleType.HONGYANG)
                        2 -> list[pos] = ArticleListFragment.newInstance(ArticleType.GUOLIN)
                        3 -> list[pos] = ArticleListFragment.newInstance(ArticleType.YUGANG)
                        4 -> list[pos] = ArticleListFragment.newInstance(ArticleType.CXMY)
                        5 -> list[pos] = ArticleListFragment.newInstance(ArticleType.XIA)
                        6 -> list[pos] = ArticleListFragment.newInstance(ArticleType.GOOGLE)
                        7 -> list[pos] = ArticleListFragment.newInstance(ArticleType.MEITUAN)
                        8 -> list[pos] = ArticleListFragment.newInstance(ArticleType.GITYUAN)
                    }
                }
                return list[pos]
            }
        }
    }

    companion object {

        fun newInstance() = HomeFragment()
    }

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun loadData(isRefresh: Boolean) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)


    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }


    fun toTopOrRefresh() {
        pagerAdapter.getItem(mBinding.viewPager.currentItem).let {
            if (it is ToTopOrRefreshContract) {
                it.toTopOrRefresh()
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            show()
        }
    }

    fun show() {
        (activity as MainActivity).needShowTab(true)
        (activity as MainActivity).setupWithViewPager(mBinding.viewPager)
    }

    override fun initView() {
        mBinding.viewPager.offscreenPageLimit = pagerAdapter.count
        mBinding.viewPager.adapter = pagerAdapter
        show()
    }


}