package com.ditclear.paonet.aop.aspect

import android.util.Log
import android.view.View
import com.ditclear.paonet.BuildConfig
import com.ditclear.paonet.R
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.*


/**
 * Created by baixiaokang on 16/12/9.
 * {link https://github.com/north2016/T-MVP/blob/master/app/src/main/java/com/aop/SingleClickAspect.java}
 * 防止View被连续点击,间隔时间600ms
 */

@Aspect
class SingleClickAspect {

    @Pointcut("execution(@com.ditclear.paonet.aop.annotation.SingleClick * *(..))") //方法切入点
    fun methodAnnotated() {

    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) view = arg
        }
        if (view != null) {
            val tag = view.getTag(TIME_TAG)
            val lastClickTime = if (tag != null) tag as Long else 0
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "lastClickTime:" + lastClickTime)
            }
            val currentTime = Calendar.getInstance().timeInMillis
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime)
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "currentTime:" + currentTime)
                }
                joinPoint.proceed()//执行原方法
            }
        }
    }

    companion object {

        val TAG = "SingleClickAspect"
        val MIN_CLICK_DELAY_TIME = 600
        internal var TIME_TAG = R.id.click_time
    }
}
