package com.ditclear.paonet.lib.extention;

import static com.ditclear.paonet.lib.extention.ToastType.ERROR;
import static com.ditclear.paonet.lib.extention.ToastType.NORMAL;
import static com.ditclear.paonet.lib.extention.ToastType.SUCCESS;
import static com.ditclear.paonet.lib.extention.ToastType.WARNING;

import android.support.annotation.IntDef;

/**
 * 页面描述：ToastType
 *
 * Created by ditclear on 2017/10/11.
 */
@IntDef({ERROR,NORMAL,SUCCESS,WARNING})
public @interface ToastType {
    int ERROR=-2;
    int WARNING=-1;
    int NORMAL=0;
    int SUCCESS=1;
}
