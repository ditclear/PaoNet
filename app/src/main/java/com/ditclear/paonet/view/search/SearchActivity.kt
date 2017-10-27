package com.ditclear.paonet.view.search

import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.transition.Slide
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.SearchActivityBinding
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.helper.Utils


/**
 * 页面描述：SearchActivity 搜索
 *
 * Created by ditclear on 2017/10/21.
 */
class SearchActivity : BaseActivity<SearchActivityBinding>() {

    override fun getLayoutId() = R.layout.search_activity

    override fun loadData() {
    }

    override fun initView() {
        window.enterTransition = Slide().apply { duration = 1000 }
        setSupportActionBar(mBinding.toolbar)

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    switchFragment(SearchResultFragment.newInstance(query))
                }
                Utils.hideIme(mBinding.searchView)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    needShowTab(false)
                    switchFragment(RecentSearchFragment.newInstance())
                }
                return true
            }

        })

    }

    fun setQuery(keyWord: String?) {
        keyWord?.run {
            mBinding.searchView.setQuery(keyWord, false)
            Utils.hideIme(mBinding.searchView)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 切换fragment
     */
    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()

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