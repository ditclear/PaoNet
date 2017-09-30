package com.ditclear.paonet.view

import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.ditclear.paonet.R
import com.ditclear.paonet.databinding.MainActivityBinding
import com.ditclear.paonet.view.home.HomeFragment

class MainActivity : BaseActivity<MainActivityBinding>(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.main_activity

    override fun loadData() {

    }

    public fun syncToolBar(toolbar:Toolbar){
        val toggle = ActionBarDrawerToggle(
                this, mBinding.drawerLayout,toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        mBinding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun initView() {



        mBinding.navView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
    }

    override fun onBackPressed() {

        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        return super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        mBinding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}