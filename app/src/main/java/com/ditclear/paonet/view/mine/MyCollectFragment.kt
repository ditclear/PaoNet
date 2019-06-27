package com.ditclear.paonet.view.mine

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.helper.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.MainActivity

/**
 * 页面描述：MyCollectFragment
 *
 * Created by ditclear on 2017/10/15.
 */

class MyCollectFragment : BaseFragment<HomeFragmentBinding>() {


    private val collectAdapter: androidx.fragment.app.FragmentStatePagerAdapter by lazy {
        object : AbstractPagerAdapter(childFragmentManager, arrayOf("文章", "代码")) {
            override fun getItem(pos: Int): androidx.fragment.app.Fragment? {
                if (list[pos] == null) {
                    when (pos) {
                        0 -> list[pos] = CollectionListFragment.newInstance(1)
                        1 -> list[pos] = CollectionListFragment.newInstance(-19)
                    }
                }
                return list[pos]
            }
        }
    }

    companion object {

        fun newInstance() = MyCollectFragment()
    }

    override fun getLayoutId(): Int = R.layout.home_fragment


    override fun loadData(isRefresh: Boolean) {

    }

    override fun initArgs(savedInstanceState: Bundle?) {

    }

    override fun initView() {

        mBinding.viewPager.adapter = collectAdapter
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