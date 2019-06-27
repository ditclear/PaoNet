package com.ditclear.paonet.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.ContentMainBinding
import com.ditclear.paonet.helper.adapter.viewpager.AbstractPagerAdapter
import com.ditclear.paonet.view.article.ArticleListFragment
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.code.CodeListFragment

/**
 * 页面描述：SearchResultFragment
 *
 * Created by ditclear on 2017/10/24.
 */
class SearchResultFragment : BaseFragment<ContentMainBinding>() {

    override fun getLayoutId() = R.layout.content_main

    val keyWord by lazy { autoWired(KEY_KEYWORD,"")?:"" }

    private val pagerAdapter: androidx.fragment.app.FragmentStatePagerAdapter by lazy {
        object : AbstractPagerAdapter(childFragmentManager, arrayOf("文章", "代码")) {
            override fun getItem(pos: Int): androidx.fragment.app.Fragment? {
                if (list[pos] == null) {
                    when (pos) {
                        0 -> list[pos] = ArticleListFragment.newInstance(keyWord)
                        1 -> list[pos] = CodeListFragment.newInstance(keyWord)
                    }
                }
                return list[pos]
            }

        }
    }

    companion object {
        private val KEY_KEYWORD = "keyWord"
        fun newInstance(keyWord: String): SearchResultFragment {

            return SearchResultFragment().apply {
                val bundle = Bundle()
                bundle.putString(KEY_KEYWORD, keyWord)
                arguments = bundle
            }
        }

    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun initView() {

        mBinding.viewPager.adapter = pagerAdapter
        mBinding.viewPager.offscreenPageLimit = pagerAdapter.count
        (activity as SearchActivity).needShowTab(true)
        (activity as SearchActivity).setupWithViewPager(mBinding.viewPager)
    }

}