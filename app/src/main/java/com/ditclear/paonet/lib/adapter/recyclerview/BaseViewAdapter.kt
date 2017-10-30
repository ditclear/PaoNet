package com.ditclear.paonet.lib.adapter.recyclerview

import android.content.Context
import android.databinding.ObservableList
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.ditclear.paonet.BR

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/10/30.
 */
abstract class BaseViewAdapter<T>(context: Context, private val list: ObservableList<T>) : RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    protected val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    var itemPresenter: com.ditclear.paonet.lib.adapter.recyclerview.ItemClickPresenter<T>? = null

    var itemDecorator: ItemDecorator? = null


    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>?, position: Int) {
        val item = list[position]
        holder?.binding?.setVariable(BR.item, item)
        holder?.binding?.setVariable(BR.presenter, itemPresenter)
        holder?.binding?.executePendingBindings()
        itemDecorator?.decorator(holder, position, getItemViewType(position))
    }

    override fun getItemCount(): Int =list.size

    fun getItem(position: Int): T? = list[position]

}