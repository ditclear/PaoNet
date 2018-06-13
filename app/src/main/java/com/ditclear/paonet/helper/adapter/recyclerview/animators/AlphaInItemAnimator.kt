package com.ditclear.paonet.helper.adapter.recyclerview.animators

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.LinearInterpolator
import com.ditclear.paonet.helper.adapter.recyclerview.ItemAnimator

/**
 * 页面描述：AlphaInItemAnimator
 *
 * Created by ditclear on 2018/6/13.
 */

class AlphaInItemAnimator(private val from :Float=0f,private val duration: Long = 500L, private val interpolator: TimeInterpolator = LinearInterpolator()) : ItemAnimator {
    override fun scrollUpAnim(v: View) {
        ObjectAnimator.ofFloat(v, "alpha", from, 1f)
                .setDuration(duration).apply {
                    interpolator = this@AlphaInItemAnimator.interpolator
                }
                .start()
    }

    override fun scrollDownAnim(v: View) {
        ObjectAnimator.ofFloat(v, "alpha", from, 1f)
                .setDuration(duration).apply {
                    interpolator = this@AlphaInItemAnimator.interpolator
                }
                .start()
    }

}