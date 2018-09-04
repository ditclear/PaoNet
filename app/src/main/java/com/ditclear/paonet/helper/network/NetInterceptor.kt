package com.ditclear.paonet.helper.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * 页面描述：默认拦截器
 *
 *
 * Created by ditclear on 2017/7/28.
 */

class NetInterceptor(private val handler: RequestHandler?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()


        if (handler != null) {
            request = handler.onBeforeRequest(request, chain)
        }

        val response = chain.proceed(request)


        if (handler != null) {
            val tmp = handler.onAfterRequest(response, chain)
            if (tmp != null) {
                return tmp
            }

        }
        return response
    }
}
