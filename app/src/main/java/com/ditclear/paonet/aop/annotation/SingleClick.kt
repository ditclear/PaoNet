package com.ditclear.paonet.aop.annotation

/**
 * Created by ditclear
 *
 * 防止View被连续点击
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class SingleClick
