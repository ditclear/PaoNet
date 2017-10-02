package com.ditclear.paonet.lib.extention

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.DimenRes
import android.support.annotation.NonNull
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.webkit.URLUtil
import android.widget.Toast
import com.ditclear.paonet.R
import com.ditclear.paonet.view.Constants
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.util.concurrent.TimeUnit


/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/29.
 */
fun Activity.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Activity.dpToPx(@DimenRes resID:Int):Int=this.resources.getDimensionPixelOffset(resID)

fun Activity.navigateToActivity(c:Class<*>,serializable: Serializable){
    val intent=Intent()
    val bundle=Bundle()
    bundle.putSerializable(Constants.KEY_SERIALIZABLE,serializable)
    intent.setClass(this,c)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun <T> Flowable<T>.async(withDelay: Long = 0): Flowable<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun Activity.navigateToWebPage(@NonNull url: String) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
        return
    }

    val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(this, R.color.theme))
            .build()

    intent.launchUrl(this, Uri.parse(url))
}

fun Fragment.navigateToWebPage(@NonNull url: String?) {
    if (TextUtils.isEmpty(url) || !URLUtil.isNetworkUrl(url)) {
        return
    }
    val intent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(activity, R.color.theme))
            .build()

    intent.launchUrl(activity, Uri.parse(url))
}

