package com.ditclear.paonet.view.mine

import android.content.Context
import android.support.v4.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.HomeFragmentBinding
import com.ditclear.paonet.di.scope.FragmentScope
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.SinglePageActivity
import javax.inject.Inject
import javax.inject.Named

/**
 * 页面描述：MyCollectFragment
 *
 * Created by ditclear on 2017/10/15.
 */
@FragmentScope
class MyCollectFragment : BaseFragment<HomeFragmentBinding>() {

    @Inject
    @field:Named("collect")
    lateinit var collectAdapter: FragmentStatePagerAdapter

    companion object {

        fun newInstance() = MyCollectFragment()
    }

    override fun getLayoutId(): Int = R.layout.home_fragment


    override fun loadData(isRefresh: Boolean) {

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        getComponent().inject(this)
    }

    override fun initView() {
        inList = false
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
         (activity as SinglePageActivity?)?.needShowTab(true)
         (activity as SinglePageActivity?)?.setupWithViewPager(mBinding.viewPager)
    }

    fun getViewPager()=mBinding.viewPager


}