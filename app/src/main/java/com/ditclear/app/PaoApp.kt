package com.ditclear.app

import android.app.Application
import com.ditclear.paonet.PaoApp

/**
 * 页面描述：
 *
 * Created by ditclear on 2017/9/26.
 */

class PaoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        PaoApp.onCreate(this)
    }
}
