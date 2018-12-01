package com.ditclear.paonet.helper

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ditclear.paonet.R
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * 页面描述：ImageUtil
 *
 * Created by ditclear on 2017/10/21.
 */

object ImageUtil {

    fun load(uri: String?, imageView: ImageView, isAvatar: Boolean = false) {
        if (!isAvatar) {
            Glide.with(imageView.context).load(uri)
                    .placeholder(R.color.tools_color)
                    .error(R.color.tools_color)
                    .into(imageView)
        } else {
            Glide.with(imageView.context).load(uri)
                    .bitmapTransform(CropCircleTransformation(imageView.context))
                    .placeholder(R.drawable.ic_face)
                    .error(R.drawable.ic_face)
                    .into(imageView)
        }
    }

}