package com.ditclear.paonet.lib.extention

import android.app.Activity
import android.widget.Toast
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/29.
 */
fun Activity.toast(msg:CharSequence,duration: Int =Toast.LENGTH_SHORT){
    Toast.makeText(this,msg,duration).show()
}

fun <T> Flowable<T>.async(withDelay:Long = 0): Flowable<T> =
        this.subscribeOn(Schedulers.io()).delay(withDelay,TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())