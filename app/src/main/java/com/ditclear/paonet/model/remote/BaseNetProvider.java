package com.ditclear.paonet.model.remote;


import android.content.Context;

import com.ditclear.paonet.BuildConfig;
import com.ditclear.paonet.lib.network.NetProvider;
import com.ditclear.paonet.lib.network.RequestHandler;
import com.ditclear.paonet.model.remote.exception.ApiException;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.util.UUID;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 页面描述：BaseNetProvider
 * <p>
 * Created by ditclear on 2017/7/28.
 */

public class BaseNetProvider implements NetProvider {

    public static final long CONNECT_TIME_OUT = 20;
    public static final long READ_TIME_OUT = 180;
    public static final long WRITE_TIME_OUT = 30;

    private Context mContext;

    public BaseNetProvider(Context context) {
        this.mContext=context;
    }

    @Override
    public Interceptor[] configInterceptors() {
        return null;
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {

    }

    @Override
    public CookieJar configCookie() {
        return  new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext));
    }

    @Override
    public RequestHandler configHandler() {

        return new HeaderHandler();
    }

    @Override
    public long configConnectTimeoutSecs() {
        return CONNECT_TIME_OUT;
    }

    @Override
    public long configReadTimeoutSecs() {
        return READ_TIME_OUT;
    }

    @Override
    public long configWriteTimeoutSecs() {
        return WRITE_TIME_OUT;
    }

    @Override
    public boolean configLogEnable() {
        return BuildConfig.DEBUG;
    }

    private String getTraceId() {
        return UUID.randomUUID().toString();
    }

    public class HeaderHandler implements RequestHandler {

        @Override
        public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
            return request;
        }

        @Override
        public Response onAfterRequest(Response response, Interceptor.Chain chain)
                throws IOException {
            ApiException e = null;
            if (401 == response.code()) {
                throw new ApiException("登录已过期,请重新登录!");
            } else if (403 == response.code()) {
                e = new ApiException("禁止访问!");
            } else if (404 == response.code()) {
                e = new ApiException("链接错误");
            } else if (503 == response.code()) {
                e = new ApiException("服务器升级中!");
            } else if (response.code() > 300) {
                String message = response.body().string();
                if (Utils.INSTANCE.check(message)) {
                    e = new ApiException("服务器内部错误!");
                } else {
                    e = new ApiException(message);
                }
            }
            if (!Utils.INSTANCE.check(e)) {
                throw e;
            }
            return response;
        }
    }
}
