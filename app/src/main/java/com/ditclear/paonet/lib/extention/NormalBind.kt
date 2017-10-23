package com.ditclear.paonet.lib.extention

import android.databinding.BindingAdapter
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.ditclear.paonet.R
import com.ditclear.paonet.view.helper.ImageUtil
import com.ditclear.paonet.viewmodel.PagedViewModel
import us.feras.mdv.MarkdownView

/**
 * 页面描述：normal bind class
 *
 * Created by ditclear on 2017/10/2.
 */

@BindingAdapter(value = *arrayOf("url","avatar"),requireAll = false)
fun bindUrl(imageView: ImageView, url: String?,isAvatar:Boolean?) {

    ImageUtil.load(url,imageView,isAvatar=isAvatar?:false)
}

@BindingAdapter(value = *arrayOf("start_color","icon"),requireAll = false)
fun bindTransitionArgs(v: View, color:Int,icon:Int?){
    v.setTag(R.integer.start_color,color)
    if (v is FloatingActionButton) {
        icon?.let { v.setTag(R.integer.fab_icon, icon) }
    }
}

@BindingAdapter(value = "markdown")
fun bindMarkDown(v:MarkdownView,markdown:String?){
    markdown?.let {
        v.setMarkdown(markdown)
    }
}

@BindingAdapter(value = "loadMore")
fun bindLoadMore(v: RecyclerView, vm:PagedViewModel){
    v.layoutManager=LinearLayoutManager(v.context)
    v.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (recyclerView!!.layoutManager is LinearLayoutManager) {
                //表示是否能向上滚动，false表示已经滚动到底部

                if (!recyclerView.canScrollVertically(1)) {
                    if (vm.loadMore.get()) {
                        vm.loadData(false)
                        //防止多次拉取同样的数据
                        vm.loadMore.set(false)
                    }
                }
            }
        }
    })
}

@BindingAdapter(value = "onRefresh")
fun bindOnRefresh(v:SwipeRefreshLayout,vm: PagedViewModel){
    v.setOnRefreshListener { vm.loadData(true) }
}

@BindingAdapter(value = *arrayOf("adapter","vertical"),requireAll = false)
fun bindSlider(v:RecyclerView,adapter: RecyclerView.Adapter<*>,vertical:Boolean=true){

    if (vertical){
        v.layoutManager=LinearLayoutManager(v.context,LinearLayoutManager.VERTICAL,false)
    }else{
        v.layoutManager=LinearLayoutManager(v.context,LinearLayoutManager.HORIZONTAL,false)
    }
    if(v.onFlingListener==null) {
        PagerSnapHelper().attachToRecyclerView(v)
    }
    v.adapter=adapter

}