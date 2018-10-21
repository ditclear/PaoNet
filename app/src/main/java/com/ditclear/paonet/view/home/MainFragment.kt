package com.ditclear.paonet.view.home

import android.support.annotation.IdRes
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.MainFragmentBinding
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.SystemBarHelper
import com.ditclear.paonet.helper.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.helper.adapter.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.navigateToSearch
import com.ditclear.paonet.helper.needsLogin
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.view.base.BaseFragment
import com.ditclear.paonet.view.home.viewmodel.CategoryItemViewModel
import com.ditclear.paonet.view.home.viewmodel.MainViewModel


class MainFragment : BaseFragment<MainFragmentBinding>(),
        ItemClickPresenter<CategoryItemViewModel> {


    override fun getLayoutId(): Int = R.layout.main_fragment

    private val viewModel by lazy {
        getInjectViewModel<MainViewModel>()
    }

    val adapter by lazy {
        SingleTypeAdapter<CategoryItemViewModel>(mContext, R.layout.code_category_list_item,
                viewModel.categories).apply {
            itemPresenter = this@MainFragment
        }
    }


    override fun onItemClick(v: View?, item: CategoryItemViewModel) {
        closeDrawer()
        changeFragment(item.catename!!)

        Navigation.findNavController(activity!!, R.id.nav_main_host)
                .navigate(R.id.codeListFragment,
                        bundleOf("cate" to item.value,
                                "inList" to false))
    }

    val defaultEmptyUser by lazy { User() }

    override fun loadData(isRefresh: Boolean) {
        viewModel.getCodeCategories().bindLifeCycle(this)
                .subscribe({}, {})
    }


    override fun onResume() {
        super.onResume()
        viewModel.user.set(SpUtil.user ?: defaultEmptyUser)

    }

    fun syncToolBar(toolbar: Toolbar) {
        activity?.let {
            val toggle = ActionBarDrawerToggle(
                    it, mBinding.drawerLayout, toolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            mBinding.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
//            SystemBarHelper.setHeightAndPadding(it,mBinding.appBar )
        }
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        mBinding.tabLayout.setupWithViewPager(viewPager)
    }

    fun needShowTab(needShow: Boolean, @TabLayout.Mode mode: Int = TabLayout.MODE_SCROLLABLE) {
        mBinding.tabLayout.tabMode = mode
        if (needShow) {
            mBinding.tabLayout.visibility = View.VISIBLE
        } else {
            mBinding.tabLayout.visibility = View.GONE
        }
    }


    override fun initView() {
        inList = false
        activity?.let {
//            it.window?.statusBarColor = it.getCompactColor(R.color.colorPrimary)
            SystemBarHelper.setStatusBarLightMode(it)
        }
        getComponent().inject(this)
        (activity as AppCompatActivity?)?.setSupportActionBar(mBinding.toolbar)
        syncToolBar(mBinding.toolbar)
        setHasOptionsMenu(true)
        mBinding.vm = viewModel
        mBinding.navMainLayout?.navCodeLayout?.recyclerView?.run {
            adapter = this@MainFragment.adapter
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        }
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                //再次点击，可见item个数大于5个回到顶部 ，小于5刷新当前页
//                homeFragment.toTopOrRefresh()
            }

        })

    }


    @SingleClick
    fun toggleLog(v: View) {
        activity?.let {
            needsLogin(R.color.hint_highlight, v, it, radius = 0)
        }
    }

    fun needCloseDrawer(): Boolean {
        return if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        } else {

            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_search -> navigateToSearch(activity)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun closeDrawer() {
        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    @CheckLogin
    private fun switchMyArticle(v: View?) {
        changeFragment(R.id.myArticleFragment, "我的文章")
        closeDrawer()
    }

    @CheckLogin
    private fun switchMyCollect(v: View?) {
        changeFragment(R.id.myCollectFragment, "我的收藏")
        closeDrawer()

    }

    /**
     * 切换fragment
     */
    private fun changeFragment(title: String = "泡在网上的日子") {
        (activity as AppCompatActivity?)?.let {
            it.supportActionBar?.title = title

        }
    }


    private fun changeFragment(@IdRes id: Int, title: String = "泡在网上的日子") {

        (activity as AppCompatActivity?)?.let {
            it.supportActionBar?.title = title
            Navigation.findNavController(it, R.id.nav_main_host).navigate(id)
        }


    }

    override fun onClick(v: View?) {
        v?.run {
            when (id) {
                R.id.toggle_btn -> toggleLog(this)
                R.id.code_tv, R.id.toggle_cate_btn -> viewModel.toggleCategory()
                R.id.home_tv -> {
                    closeDrawer()
                    changeFragment(R.id.homeFragment)
                }
                R.id.my_article_tv -> {
                    switchMyArticle(this)
                }
                R.id.my_collect_tv -> {
                    switchMyCollect(this)
                }

            }
        }
    }

}