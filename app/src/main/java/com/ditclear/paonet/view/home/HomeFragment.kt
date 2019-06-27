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
        object : AbstractPagerAdapter(childFragmentManager, arrayOf("Recent", "ANDROID", "程序设计", "前端开发", "IOS", "数据库", "开发日志", "应用推荐", "每日一站")) {
            override fun getItem(pos: Int): androidx.fragment.app.Fragment? {
                if (list[pos] == null) {
                    when (pos) {
                        0 -> list[pos] = RecentFragment.newInstance()
                        1 -> list[pos] = ArticleListFragment.newInstance(ArticleType.ANDROID)
                        2 -> list[pos] = ArticleListFragment.newInstance(ArticleType.PROGRAME)
                        3 -> list[pos] = ArticleListFragment.newInstance(ArticleType.FRONT_END)
                        4 -> list[pos] = ArticleListFragment.newInstance(ArticleType.IOS)
                        5 -> list[pos] = ArticleListFragment.newInstance(ArticleType.DB)
                        6 -> list[pos] = ArticleListFragment.newInstance(ArticleType.DEVLOG)
                        7 -> list[pos] = ArticleListFragment.newInstance(ArticleType.RECOMMAND)
                        8 -> list[pos] = ArticleListFragment.newInstance(ArticleType.DAILY)
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