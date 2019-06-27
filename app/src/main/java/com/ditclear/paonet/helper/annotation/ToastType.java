package com.ditclear.paonet.helper.annotation;

import static com.ditclear.paonet.helper.annotation.ToastType.ERROR;
import static com.ditclear.paonet.helper.annotation.ToastType.NORMAL;
import static com.ditclear.paonet.helper.annotation.ToastType.SUCCESS;
import static com.ditclear.paonet.helper.annotation.ToastType.WARNING;

import androidx.annotation.IntDef;

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
