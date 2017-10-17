package com.ditclear.paonet.view.helper;

import static com.ditclear.paonet.view.helper.ArticleType.DAILY;
import static com.ditclear.paonet.view.helper.ArticleType.RECOMMAND;
import static com.ditclear.paonet.view.helper.CodeType.ANIMATION;
import static com.ditclear.paonet.view.helper.CodeType.AUDIO;
import static com.ditclear.paonet.view.helper.CodeType.BUTTON;
import static com.ditclear.paonet.view.helper.CodeType.CALENDAR;
import static com.ditclear.paonet.view.helper.CodeType.CAMERA;
import static com.ditclear.paonet.view.helper.CodeType.CANVAS;
import static com.ditclear.paonet.view.helper.CodeType.CHART;
import static com.ditclear.paonet.view.helper.CodeType.CORE_MOTION;
import static com.ditclear.paonet.view.helper.CodeType.DATABASE;
import static com.ditclear.paonet.view.helper.CodeType.DIALOG;
import static com.ditclear.paonet.view.helper.CodeType.EBOOK;
import static com.ditclear.paonet.view.helper.CodeType.EDITTEXT;
import static com.ditclear.paonet.view.helper.CodeType.GAME;
import static com.ditclear.paonet.view.helper.CodeType.GESTURE;
import static com.ditclear.paonet.view.helper.CodeType.GRIDVIEW;
import static com.ditclear.paonet.view.helper.CodeType.GUIDE_VIEW;
import static com.ditclear.paonet.view.helper.CodeType.HUD;
import static com.ditclear.paonet.view.helper.CodeType.IMAGE;
import static com.ditclear.paonet.view.helper.CodeType.LISTVIEW;
import static com.ditclear.paonet.view.helper.CodeType.MAP;
import static com.ditclear.paonet.view.helper.CodeType.MENU;
import static com.ditclear.paonet.view.helper.CodeType.NETWORKING;
import static com.ditclear.paonet.view.helper.CodeType.OTHERS;
import static com.ditclear.paonet.view.helper.CodeType.PICKER;
import static com.ditclear.paonet.view.helper.CodeType.POPUP;
import static com.ditclear.paonet.view.helper.CodeType.PROGRESSBAR;
import static com.ditclear.paonet.view.helper.CodeType.SCROLLVIEW;
import static com.ditclear.paonet.view.helper.CodeType.SEGMENT;
import static com.ditclear.paonet.view.helper.CodeType.SHARE;
import static com.ditclear.paonet.view.helper.CodeType.SLIDER;
import static com.ditclear.paonet.view.helper.CodeType.SWITCH;
import static com.ditclear.paonet.view.helper.CodeType.SYNC;
import static com.ditclear.paonet.view.helper.CodeType.TAB_BAR;
import static com.ditclear.paonet.view.helper.CodeType.TEXTVIEW;
import static com.ditclear.paonet.view.helper.CodeType.TIP;
import static com.ditclear.paonet.view.helper.CodeType.TOOLBAR;
import static com.ditclear.paonet.view.helper.CodeType.VIEW_EFFECT;
import static com.ditclear.paonet.view.helper.CodeType.VIEW_LAYOUT;
import static com.ditclear.paonet.view.helper.CodeType.VIEW_TRANSITION;
import static com.ditclear.paonet.view.helper.CodeType.WEBVIEW;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 页面描述：CodeType 代码类型
 *
 * Created by ditclear on 2017/10/17.
 */

@IntDef({TIP,DIALOG,BUTTON,CALENDAR,CAMERA
        ,HUD,RECOMMAND,DAILY,ANIMATION,AUDIO,CANVAS
        ,IMAGE,SYNC,MAP,MENU,TOOLBAR,POPUP,PICKER,PROGRESSBAR,SCROLLVIEW
        ,SEGMENT,SLIDER,GRIDVIEW,SWITCH,TAB_BAR,LISTVIEW,EDITTEXT,TEXTVIEW
        ,WEBVIEW,CHART,GAME,CORE_MOTION,DATABASE,EBOOK,GESTURE,GUIDE_VIEW
        ,NETWORKING,SHARE,VIEW_EFFECT,VIEW_LAYOUT,VIEW_TRANSITION,OTHERS})
@Retention(RetentionPolicy.SOURCE)
public @interface CodeType {
    int TIP=500;
    int DIALOG=1000;
    int BUTTON=1500;
    int CALENDAR=2000;
    int CAMERA=2500;
    int HUD=3000;
    int IMAGE=3500;
    int FILE=4000;
    int SYNC=4500;
    int MAP=5000;
    int MENU=5500;
    int TOOLBAR=6000;
    int PICKER=6500;
    int PROGRESSBAR=7000;
    int SCROLLVIEW=7500;
    int SEGMENT=8000;
    int SLIDER=8500;
    int GRIDVIEW=9000;
    int SWITCH=9500;
    int TAB_BAR=10000;
    int LISTVIEW=10500;
    int EDITTEXT=11000;
    int TEXTVIEW=11500;
    int WEBVIEW=12000;
    int ANIMATION=12500;
    int AUDIO=13000;
    int CHART=13500;
    int GAME=14000;
    int CORE_MOTION=14500;
    int DATABASE=15000;
    int CANVAS=15500;
    int EBOOK=16000;
    int GESTURE=16500;
    int GUIDE_VIEW=17000;
    int NETWORKING=17500;
    int POPUP=18000;
    int SHARE=18500;
    int VIEW_EFFECT=19000;
    int VIEW_LAYOUT=19500;
    int VIEW_TRANSITION=20000;
    int OTHERS=20500;
}
