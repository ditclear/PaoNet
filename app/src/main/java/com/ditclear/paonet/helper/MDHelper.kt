package com.ditclear.paonet.helper

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import android.view.View

/**
 * 页面描述：MDHelper
 *
 * Created by ditclear on 2018/8/8.
 */
object MDHelper{



    fun makeFling(v:View){

        FlingAnimation(v, DynamicAnimation.SCROLL_Y).apply {
            setStartVelocity(-100f)
            setMinValue(0f)
            setMaxValue(200f)
            friction = 1.1f
            start()
        }

    }
}