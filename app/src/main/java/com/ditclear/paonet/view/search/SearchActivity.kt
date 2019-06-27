package com.ditclear.paonet.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.widget.SearchView
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.SearchActivityBinding
import com.ditclear.paonet.helper.SystemBarHelper
import com.ditclear.paonet.helper.SystemBarHelper.setStatusBarDarkMode
import com.ditclear.paonet.helper.Utils
import com.ditclear.paonet.helper.extens.switchFragment
import com.ditclear.paonet.view.base.BaseActivity


/**
 * 页面描述：SearchActivity 搜索
 *
 * Created by ditclear on 2017/10/21.
 */
class SearchActivity : BaseActivity<SearchActivityBinding>() {

    override fun getLayoutId() = R.layout.search_activity

    val recentSearch by lazy { RecentSearchFragment.newInstance() }

    override fun loadData(isRefresh:Boolean) {

    }

    override fun initView() {

        setStatusBarDarkMode(this)
        setSupportActionBar(mBinding.toolbar)
        delayToTransition = true
        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    changeFragment(SearchResultFragment.newInstance(this))
                }
                Utils.hideIme(mBinding.searchView)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    needShowTab(false)
                    changeFragment(recentSearch)
                }
                return true
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            changeFragment(recentSearch)
        }
    }

    fun setQuery(keyWord: String?) {
        keyWord?.run {
            mBinding.searchView.setQuery(keyWord, true)
            Utils.hideIme(mBinding.searchView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    var temp: androidx.fragment.app.Fragment? = null
    /**
     * 切换fragment
     */
    private fun changeFragment(fragment: androidx.fragment.app.Fragment) {

        switchFragment(temp, fragment)
        temp = fragment
    }

    fun setupWithViewPager(viewPager: androidx.viewpager.widget.ViewPager) {
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