package com.ditclear.paonet;

import android.app.Application;

import com.ditclear.paonet.lib.network.NetMgr;
import com.ditclear.paonet.model.remote.BaseNetProvider;

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

public class PaoApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        NetMgr.registerProvider(new BaseNetProvider());
    }
}
