package com.ditclear.paonet.lib.network;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 页面描述：默认拦截器
 * <p>
 * Created by ditclear on 2017/7/28.
 */

public class NetInterceptor implements Interceptor {
    private RequestHandler handler;

    public NetInterceptor(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        if (handler != null) {
            request = handler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (handler != null) {
            Response tmp = handler.onAfterRequest(response, chain);
            if (tmp != null) {
                return tmp;
            }

        }
        return response;
    }
}
