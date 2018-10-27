package com.ditclear.paonet.helper.widget.indicator

import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable



/**
 * 页面描述：ShapeHolder
 *
 * Created by ditclear on 2017/11/6.
 */
class ShapeHolder(var shape: ShapeDrawable?) {
    var x = 0f
    var y = 0f//Ô²µÄx¡¢y×ø±ê
    var color: Int = 0
        set(value) {
            shape!!.paint.color = value
            field = value

        }
    private var alpha = 1f
    var paint: Paint? = null

    fun setAlpha(alpha: Float) {
        this.alpha = alpha
        shape!!.alpha = (alpha * 255f + .5f).toInt()
    }

    var width: Float
        get() = shape!!.shape.width
        set(width) {
            val s = shape!!.shape
            s.resize(width, s.height)
        }

    var height: Float
        get() = shape!!.shape.height
        set(height) {
            val s = shape!!.shape
            s.resize(s.width, height)
        }

    fun resizeShape(width: Float, height: Float) {
        shape!!.shape.resize(width, height)
    }
}