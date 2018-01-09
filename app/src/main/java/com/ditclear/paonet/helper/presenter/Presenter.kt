package com.ditclear.paonet.helper.presenter

import android.view.View

/**
 * 页面描述：Presenter
 *
 * Created by ditclear on 2017/11/2.
 */
interface Presenter:View.OnClickListener{

    override fun onClick(v: View?)
}