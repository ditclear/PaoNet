package com.ditclear.paonet.helper.adapter.recyclerview.animators

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.LinearInterpolator
import com.ditclear.paonet.helper.adapter.recyclerview.ItemAnimator

/**
 * 页面描述：ScaleInItemAnimator
 *
 * Created by ditclear on 2018/6/13.
 */
class ScaleInItemAnimator(private val from: Float = .6f, private val duration: Long = 500L, private val interpolator: TimeInterpolator = LinearInterpolator()) : ItemAnimator {

    override fun scrollUpAnim(v: View) {
        getAnimators(v).forEach {
            it.setDuration(duration).apply {
                interpolator = this@ScaleInItemAnimator.interpolator
            }.start()
        }
    }

    override fun scrollDownAnim(v: View) {
        getAnimators(v).forEach {
            it.setDuration(duration).apply {
                interpolator = this@ScaleInItemAnimator.interpolator
            }.start()
        }
    }

    fun getAnimators(view: View): Array<Animator> {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", from, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", from, 1f)
        return arrayOf(scaleX, scaleY)
    }


}