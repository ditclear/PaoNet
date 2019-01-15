package com.ditclear.paonet.helper.adapter.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * 页面描述：fragment PagerAdapter
 *
 * Created by ditclear on 2017/9/30.
 */

abstract class AbstractPagerAdapter(fm: FragmentManager, var title: Array<String>? = null) : FragmentStatePagerAdapter(fm) {
    var list: MutableList<Fragment?> = mutableListOf()

    init {
        title?.iterator()?.forEach { list.add(null) }
    }

    override fun getCount(): Int = title?.size ?: 0

    abstract override fun getItem(pos: Int): Fragment?

    override fun getPageTitle(position: Int): CharSequence = title?.get(position) ?: ""


    fun initWith(title: Array<String>?) {
        this.title = title
        title?.iterator()?.forEach { list.add(null) }
        notifyDataSetChanged()
    }
}