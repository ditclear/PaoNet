package com.ditclear.paonet.helper.network

import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * 页面描述：网络配置
 *
 *
 * Created by ditclear on 2017/7/28.
 */

interface NetProvider {

    fun configInterceptors(): Array<Interceptor>?

    fun configHttps(builder: OkHttpClient.Builder)

    fun configCookie(): CookieJar?

    fun configHandler(): RequestHandler

    fun configConnectTimeoutSecs(): Long

    fun configReadTimeoutSecs(): Long

    fun configWriteTimeoutSecs(): Long

    fun configLogEnable(): Boolean

}
