package com.ditclear.paonet.helper.binds;

import androidx.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 页面描述：ImageBinds
 *
 * Created by ditclear on 2018/2/5.
 */

public class ImageBinds {


    @BindingAdapter("url")
    public static void bindImgUrl(ImageView imageView,String url){
        Glide.with(imageView.getContext()).load(url).into(imageView);
    }
}
