package com.ditclear.paonet.view.search

import android.app.SearchManager
import android.app.SharedElementCallback
import android.content.Context
import android.graphics.Point
import android.support.v7.widget.SearchView
import android.text.InputType
import android.transition.TransitionSet
import android.view.View
import android.view.inputmethod.EditorInfo
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.SearchActivityBinding
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.transitions.CircularReveal
import com.ditclear.paonet.view.transitions.TransitionUtils


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
        setupSearchView();
        setupTransitions();
    }

    private fun setupSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        mBinding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        // hint, inputType & ime options seem to be ignored from XML! Set in code
        mBinding.searchView.queryHint = getString(R.string.search_hint)
        mBinding.searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        mBinding.searchView.imeOptions = mBinding.searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(
                    sharedElementNames: List<String>,
                    sharedElements: List<View>?,
                    sharedElementSnapshots: List<View>) {
                if (sharedElements != null && !sharedElements.isEmpty()) {
                    val searchIcon = sharedElements[0]
                    if (searchIcon.id !== R.id.searchback) return
                    val centerX = (searchIcon.left + searchIcon.right) / 2
                    val hideResults = TransitionUtils.findTransition(
                            window.returnTransition as TransitionSet,
                            CircularReveal::class.java, R.id.results_container) as CircularReveal?
                    hideResults?.setCenter(Point(centerX, 0))
                }
            }
        })
    }
}