package com.ditclear.paonet.lib.extention

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ditclear.paonet.R
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * 页面描述：normal bind class
 *
 * Created by ditclear on 2017/10/2.
 */

@BindingAdapter(value = *arrayOf("url","avatar"),requireAll = false)
fun bindUrl(imageView: ImageView, url: String,isAvatar:Boolean?) {
    if (isAvatar==null||isAvatar==false) {
        Glide.with(imageView.context).load(url)
                .placeholder(R.color.tools_color)
                .error(R.color.tools_color).into(imageView)
    }else{
        Glide.with(imageView.context).load(url)
                .bitmapTransform(CropCircleTransformation(imageView.context))
                .placeholder(R.drawable.ic_face)
                .error(R.drawable.ic_face).into(imageView)
    }
}

