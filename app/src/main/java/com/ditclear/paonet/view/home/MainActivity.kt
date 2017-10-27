package com.ditclear.paonet.view.home

import android.databinding.DataBindingUtil
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.aop.annotation.CheckLogin
import com.ditclear.paonet.aop.annotation.SingleClick
import com.ditclear.paonet.databinding.MainActivityBinding
import com.ditclear.paonet.databinding.NavHeaderMainBinding
import com.ditclear.paonet.lib.extention.async
import com.ditclear.paonet.lib.extention.switchFragment
import com.ditclear.paonet.lib.extention.toast
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.view.BaseActivity
import com.ditclear.paonet.view.code.CodeFragment
import com.ditclear.paonet.view.helper.SpUtil
import com.ditclear.paonet.view.helper.navigateToSearch
import com.ditclear.paonet.view.helper.needsLogin
import com.ditclear.paonet.view.mine.MyArticleFragment
import com.ditclear.paonet.view.mine.MyCollectFragment
import io.reactivex.Single


class MainActivity : BaseActivity<MainActivityBinding>(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.main_activity

    var temp: Fragment? = null

    private val homeFragment = HomeFragment.newInstance()
    private val codeFragment = CodeFragment.newInstance()
    private val myArticleFragment = MyArticleFragment.newInstance()
    private val myCollectFragment = MyCollectFragment.newInstance()

    val defaultUser: User by lazy { User() }

    val navHeaderBinding by lazy { DataBindingUtil.bind<NavHeaderMainBinding>(mBinding.navView.getHeaderView(0)) }

    override fun loadData() {

    }

    override fun onResume() {
        super.onResume()
        navHeaderBinding?.item = SpUtil.user ?: defaultUser

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
        supportActionBar?.title = "泡在网上的日子"
//        mBinding.settingBtn.setOnClickListener { navigateToActivity(SettingsActivity::class.java) }
//        mBinding.aboutBtn.setOnClickListener { toast("about") }

        navHeaderBinding.toggleBtn.setOnClickListener { view -> toggleLog(view) }

        mBinding.navView.setNavigationItemSelectedListener(this)

        changeFragment(homeFragment)
        mBinding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)

            }
        })
    }

    @SingleClick
    fun toggleLog(v: View) {
        needsLogin(R.color.hint_highlight, v, this,radius = 0)
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
                        .compose(bindToLifecycle())
                        .async(2000)
                        .subscribe { t1, _ -> t1.let { isQuit = false } }
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        when (id) {
            R.id.nav_home -> {
                changeFragment(homeFragment)
            }
            R.id.nav_code -> {
                changeFragment(codeFragment, "Code")
            }
            R.id.nav_article -> switchMyArticle(mBinding.navView.findViewById(id))
            R.id.nav_collect -> switchMyCollect(item.actionView)
        }
        return true
    }

    @CheckLogin
    private fun switchMyArticle(v: View?) {
        changeFragment(myArticleFragment, "我的文章")
    }

    @CheckLogin
    private fun switchMyCollect(v: View?) {
        changeFragment(myCollectFragment, "我的收藏")
    }

    /**
     * 切换fragment
     */
    private fun changeFragment(fragment: Fragment, title: String = "泡在网上的日子") {
        supportActionBar?.title = title
        switchFragment(temp, fragment)
        temp = fragment
    }
}