package com.ditclear.paonet.aop.aspect

import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.helper.SpUtil
import com.ditclear.paonet.helper.needsLogin
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * 页面描述：CheckLoginAspect  拦截未登录
 *
 * Created by ditclear on 2017/10/19.
 */

@Aspect
class CheckLoginAspect {

    @Pointcut("execution(@com.ditclear.paonet.aop.annotation.CheckLogin * *(..))") //方法切入点
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
            if (null == SpUtil.user) {
                val startColor: Int = view.getTag(R.integer.start_color) as Int?
                        ?: R.color.colorAccent
                needsLogin(startColor, view)
            }else{
                joinPoint.proceed()//执行原方法
            }
        }else{
            joinPoint.proceed()//执行原方法
        }

    }

}