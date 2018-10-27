package com.ditclear.paonet.view.search

import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.SearchActivityBinding
import com.ditclear.paonet.helper.SystemBarHelper
import com.ditclear.paonet.helper.Utils
import com.ditclear.paonet.helper.extens.getCompactColor
import com.ditclear.paonet.view.base.BaseFragment


/**
 * 页面描述：SearchFragment 搜索
 *
 * Created by ditclear on 2017/10/21.
 */
class SearchFragment : BaseFragment<SearchActivityBinding>() {

    override fun getLayoutId() = R.layout.search_activity

    override fun loadData(isRefresh:Boolean) {

    }

    override fun initView() {
        inList = false
        activity?.let {
            it.window?.statusBarColor = it.getCompactColor(android.R.color.transparent)
            SystemBarHelper.setStatusBarDarkMode(it)
        }
        mBinding.toolbar.setNavigationOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
//                    changeFragment(SearchResultFragment.newInstance(this))
                    Navigation.findNavController(activity!!,R.id.nav_search_host)
                            .navigate(R.id.searchResultFragment, bundleOf("keyWord" to it))
                }
                Utils.hideIme(mBinding.searchView)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    needShowTab(false)
                    Navigation.findNavController(activity!!,R.id.nav_search_host)
                            .navigate(R.id.recentSearchFragment)
                }
                return true
            }

        })
    }

    fun setQuery(keyWord: String?) {
        keyWord?.run {
            mBinding.searchView.setQuery(keyWord, true)
            Utils.hideIme(mBinding.searchView)
        }
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        mBinding.tabLayout.setupWithViewPager(viewPager)
    }

    fun needShowTab(needShow: Boolean) {
        if (needShow) {
            mBinding.tabLayout.visibility = View.VISIBLE
        } else {
            mBinding.tabLayout.visibility = View.GONE
        }
    }
}