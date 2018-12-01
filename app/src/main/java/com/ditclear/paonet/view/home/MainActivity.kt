package com.ditclear.paonet.view.home

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.MainActivityBinding
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.helper.adapter.recyclerview.SingleTypeAdapter
import com.ditclear.paonet.helper.extens.async
import com.ditclear.paonet.helper.extens.bindLifeCycle
import com.ditclear.paonet.helper.extens.switchFragment
import com.ditclear.paonet.helper.extens.toast
import com.ditclear.paonet.helper.navigateToSearch
import com.ditclear.paonet.helper.needsLogin
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.view.base.BaseActivity
import com.ditclear.paonet.view.code.CodeListFragment
import com.ditclear.paonet.view.home.viewmodel.CategoryItemViewModel
import com.ditclear.paonet.view.home.viewmodel.MainViewModel
import com.ditclear.paonet.view.mine.MyArticleFragment
import com.ditclear.paonet.view.mine.MyCollectFragment
import io.reactivex.Single


class MainActivity : BaseActivity<MainActivityBinding>(),
        ItemClickPresenter<CategoryItemViewModel> {


    override fun getLayoutId(): Int = R.layout.main_activity

    private val viewModel by lazy {
        getInjectViewModel<MainViewModel>()
    }

    val adapter by lazy {
        SingleTypeAdapter<CategoryItemViewModel>(mContext, R.layout.code_category_list_item,
                viewModel.categories).apply {
            itemPresenter = this@MainActivity
        }
    }


    override fun onItemClick(v: View?, item: CategoryItemViewModel) {
        closeDrawer()
        changeFragment(CodeListFragment.newInstance(item.value), item.catename!!)
    }

    var temp: Fragment? = null

    val defaultEmptyUser by lazy { User() }

    private val homeFragment = HomeFragment.newInstance()
    private val myArticleFragment = MyArticleFragment.newInstance()
    private val myCollectFragment = MyCollectFragment.newInstance()


    override fun loadData(isRefresh:Boolean) {
        viewModel.getCodeCategories().bindLifeCycle(this)
                .subscribe ({},{})
    }


    override fun onResume() {
        super.onResume()
        viewModel.user.set(SpUtil.user?:defaultEmptyUser)

    }

    fun syncToolBar(toolbar: Toolbar) {
        val toggle = ActionBarDrawerToggle(
                this, mBinding.drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        mBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
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

        setSupportActionBar(mBinding.toolbar)
        syncToolBar(mBinding.toolbar)
        mBinding.vm = viewModel
        mBinding.navMainLayout?.navCodeLayout?.recyclerView?.run {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(mContext)
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        }
        mBinding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
                //再次点击，可见item个数大于5个回到顶部 ，小于5刷新当前页
                homeFragment.toTopOrRefresh()
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState ==null){
            changeFragment(homeFragment)
        }
    }

    @SingleClick
    fun toggleLog(v: View) {
        needsLogin(R.color.hint_highlight, v, this, radius = 0)
    }

    var isQuit = false;

    override fun onBackPressed() {

        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (!isQuit) {
                toast(msg = "再按一次退出程序")
                isQuit = true;
                //在两秒钟之后isQuit会变成false
                Single.just(isQuit)
                        .async(2000)
                        .bindLifeCycle(this)
                        .subscribe({isQuit = false },{})
            } else {
                super.onBackPressed()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_search -> {
                navigateToSearch(this)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun closeDrawer(){
        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    @CheckLogin
    private fun switchMyArticle(v: View?) {
        changeFragment(myArticleFragment, "我的文章")
        closeDrawer()
    }

    @CheckLogin
    private fun switchMyCollect(v: View?) {
        changeFragment(myCollectFragment, "我的收藏")
        closeDrawer()

    }

    /**
     * 切换fragment
     */
    private fun changeFragment(fragment: Fragment, title: String = "泡在网上的日子") {
        supportActionBar?.title = title
        switchFragment(temp, fragment,fragment.javaClass.simpleName)
        temp = fragment
    }

    override fun onClick(v: View?) {
        v?.run {
            when (id) {
                R.id.toggle_btn -> toggleLog(this)
                R.id.code_tv,R.id.toggle_cate_btn -> viewModel.toggleCategory()
                R.id.home_tv ->{
                    closeDrawer()
                    changeFragment(homeFragment)
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