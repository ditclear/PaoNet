package com.ditclear.paonet.aop.annotation


/**
 * Created by ditclear
 *
 * 拦截未登录
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class CheckLogin
