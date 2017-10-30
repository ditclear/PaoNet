/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ditclear.paonet.widget.recyclerview

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ditclear.paonet.BR
import com.ditclear.paonet.lib.adapter.recyclerview.BindingViewHolder
import com.ditclear.paonet.lib.adapter.recyclerview.ItemClickPresenter
import com.ditclear.paonet.lib.adapter.recyclerview.ItemDecorator
import java.util.*

/**
 * Super simple multi-type adapter using data-binding.
 *
 * @author markzhai on 16/8/23
 */
class MultiTypeAdapter @JvmOverloads constructor(context: Context, viewTypeToLayoutMap: Map<Int, Int>? = null) : RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    protected val mLayoutInflater: LayoutInflater

    protected var mCollection: MutableList<Any> = mutableListOf()
    protected var itemPresenter: ItemClickPresenter<*>? = null
    protected var mDecorator: ItemDecorator? = null

    fun setDecorator(decorator: ItemDecorator) {
        this.mDecorator = decorator
    }

    fun setPresenter(presenter: ItemClickPresenter<*>){
        itemPresenter=presenter
    }

    interface MultiViewTyper {
        fun getViewType(item: Any): Int
    }

    protected var mCollectionViewType: ArrayList<Int>

    private val mItemTypeToLayoutMap = ArrayMap<Int, Int>()


    init {
        mLayoutInflater = context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mCollectionViewType = ArrayList()
        if (viewTypeToLayoutMap != null && !viewTypeToLayoutMap.isEmpty()) {
            mItemTypeToLayoutMap.putAll(viewTypeToLayoutMap)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        return BindingViewHolder(
                DataBindingUtil.inflate<ViewDataBinding>(mLayoutInflater, getLayoutRes(viewType), parent, false))
    }


    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val item = mCollection[position]
        holder.binding.setVariable(BR.item, item)
        holder.binding.setVariable(BR.presenter, itemPresenter)
        holder.binding.executePendingBindings()
        if (mDecorator != null) {
            mDecorator!!.decorator(holder, position, getItemViewType(position))
        }
    }

    override fun getItemCount(): Int {
        return mCollection.size
    }


    fun addViewTypeToLayoutMap(viewType: Int?, layoutRes: Int?) {
        mItemTypeToLayoutMap.put(viewType, layoutRes)
    }

    override fun getItemViewType(position: Int): Int {
        return mCollectionViewType[position]
    }

    operator fun set(viewModels: List<Any>?, viewType: Int) {
        mCollection.clear()
        mCollectionViewType.clear()

        if (viewModels == null) {
            add(null, viewType)
        } else {
            addAll(viewModels, viewType)
        }
    }

    operator fun set(viewModels: List<Any>, viewTyper: MultiViewTyper) {
        mCollection.clear()
        mCollectionViewType.clear()

        addAll(viewModels, viewTyper)
    }

    fun add(viewModel: Any?, viewType: Int) {
        mCollection.add(viewModel!!)
        mCollectionViewType.add(viewType)
        notifyItemInserted(0)
    }

    fun add(position: Int, viewModel: Any, viewType: Int) {
        mCollection.add(position, viewModel)
        mCollectionViewType.add(position, viewType)
        notifyItemInserted(position)
    }

    fun addAll(viewModels: List<Any>, viewType: Int) {
        mCollection.addAll(viewModels)
        for (i in viewModels.indices) {
            mCollectionViewType.add(viewType)
        }
        notifyDataSetChanged()
    }

    fun addAll(position: Int, viewModels: List<Any>, viewType: Int) {
        mCollection.addAll(position, viewModels)
        for (i in viewModels.indices) {
            mCollectionViewType.add(position + i, viewType)
        }
        notifyItemRangeChanged(position, viewModels.size - position)
    }

    fun addAll(viewModels: List<Any>, multiViewTyper: MultiViewTyper) {
        mCollection.addAll(viewModels)
        for (i in viewModels.indices) {
            mCollectionViewType.add(multiViewTyper.getViewType(viewModels[i]))
        }
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        mCollectionViewType.removeAt(position)
        mCollection.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        mCollectionViewType.clear()
        mCollection.clear()
        notifyDataSetChanged()
    }

    @LayoutRes
    protected fun getLayoutRes(viewType: Int) = mItemTypeToLayoutMap[viewType]!!
}
