package com.ditclear.paonet.lib.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 页面描述：网络拦截
 * <p>
 * Created by ditclear on 2017/7/28.
 */

public interface RequestHandler {

    Request onBeforeRequest(Request request, Interceptor.Chain chain);

    Response onAfterRequest(Response response, Interceptor.Chain chain) throws IOException;

}
