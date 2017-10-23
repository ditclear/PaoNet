package com.ditclear.paonet.lib.adapter.viewpager

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

/**
 * 页面描述：SimplePagerAdapter
 *
 * Created by ditclear on 2017/10/23.
 */
abstract class SimplePagerAdapter<T>(private val fm: FragmentManager, val list:ObservableArrayList<T>?) : FragmentPagerAdapter(fm){

    init {
        list?.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>(){
            override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onChanged(sender: ObservableList<T>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

        })
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        return super.instantiateItem(container, position)

    }
    abstract override fun getItem(position: Int): Fragment ?

    override fun getCount()=list?.size ?:0

    override fun getItemPosition(`object`: Any?): Int {
        return PagerAdapter.POSITION_NONE
    }

}