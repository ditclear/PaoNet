package com.ditclear.paonet.view.mine

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.lib.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.view.BaseFragment
import com.ditclear.paonet.view.MainActivity

/**
 * 页面描述：MyCollectFragment
 *
 * Created by ditclear on 2017/10/15.
 */
@FragmentScope
class MyCollectFragment : BaseFragment<HomeFragmentBinding>() {

    lateinit var pagerAdapter: FragmentStatePagerAdapter

    companion object {

        fun newInstance() = MyCollectFragment()
    }

    override fun getLayoutId(): Int = R.layout.home_fragment


    override fun loadData(isRefresh: Boolean) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun initView() {
        pagerAdapter= object :AbstractPagerAdapter(childFragmentManager, arrayOf("文章", "代码")) {
                override fun getItem(pos: Int): Fragment? {
                    if (list[pos]==null) {
                        when (pos) {
                            0 -> list[pos] = CollectionListFragment.newInstance(1)
                            1 -> list[pos] = CollectionListFragment.newInstance(-19)
                        }
                    }
                    return list[pos]
                }
        }

        mBinding.viewPager.adapter = pagerAdapter
        show()
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            show()
        }
    }
     fun show(){
        (activity as MainActivity).needShowTab(true)
        (activity as MainActivity).setupWithViewPager(getViewPager())
    }

    fun getViewPager()=mBinding.viewPager


}