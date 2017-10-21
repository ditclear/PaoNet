package com.ditclear.paonet.lib.extention

import android.databinding.BindingAdapter
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.ImageView
import com.ditclear.paonet.R
import com.ditclear.paonet.view.helper.ImageUtil

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
