package com.ditclear.paonet.lib.extention

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.NonNull
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.webkit.URLUtil
import android.widget.Toast
import com.ditclear.paonet.BuildConfig
import com.ditclear.paonet.R
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.remote.exception.EmptyException
import com.ditclear.paonet.view.helper.Constants
import es.dmoral.toasty.Toasty
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit


/**
 * 页面描述：一些扩展
 *
 * Created by ditclear on 2017/9/29.
 */

fun Activity.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Activity.toast(msg: CharSequence, duration: Int = Toast.LENGTH_SHORT, @ToastType type: Int = ToastType.NORMAL) {
    when (type) {
        ToastType.WARNING -> Toasty.warning(this, msg, duration, true).show()
        ToastType.ERROR -> Toasty.error(this, msg, duration, true).show()
        ToastType.NORMAL -> Toasty.info(this, msg, duration, false).show()
        ToastType.SUCCESS -> Toasty.success(this, msg, duration, true).show()
    }
}

fun Activity.dispatchFailure(error:Throwable) {
    if (BuildConfig.DEBUG){
        error.printStackTrace()
    }
    if (error !is EmptyException) {
        error.message?.let { toast(it, ToastType.ERROR) }
    }else if (error is SocketTimeoutException) {
        error.message?.let { toast("网络连接超时", ToastType.ERROR) }

    } else if (error is UnknownHostException || error is ConnectException) {
        //网络未连接
        error.message?.let { toast("网络未连接", ToastType.ERROR) }

    }
}

fun AppCompatActivity.switchFragment(current: Fragment?, targetFg: Fragment) {
    val ft = supportFragmentManager.beginTransaction()
    current?.run { ft.hide(this) }
    if (!targetFg.isAdded)
        ft.add(R.id.container, targetFg);
    ft.show(targetFg);
    ft.commitAllowingStateLoss();
}

fun Activity.dpToPx(@DimenRes resID: Int): Int = this.resources.getDimensionPixelOffset(resID)

fun Activity.navigateToActivity(c: Class<*>, serializable: Serializable? = null) {
    val intent = Intent()
    serializable?.let {
        val bundle = Bundle()
        bundle.putSerializable(Constants.KEY_SERIALIZABLE, it)
        intent.putExtras(bundle)
    }
    intent.setClass(this, c)
    startActivity(intent)
}

fun <T> Flowable<T>.async(withDelay: Long = 0): Flowable<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.async(withDelay: Long = 0): Single<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())

fun <R : BaseResponse> Single<R>.getOriginData(): Single<R> {
    return this.compose({ upstream ->
        upstream.flatMap { t: R ->
            with(t) {
                if (t.success == 1) {
                    return@flatMap Single.just(t)
                } else {
                    return@flatMap Single.error<R>(Throwable(message))
                }
            }
        }
    })
}

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

