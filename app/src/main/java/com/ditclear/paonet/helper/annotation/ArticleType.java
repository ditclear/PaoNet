package com.ditclear.paonet.helper.annotation;

import static com.ditclear.paonet.helper.annotation.ArticleType.ANDROID;
import static com.ditclear.paonet.helper.annotation.ArticleType.DAILY;
import static com.ditclear.paonet.helper.annotation.ArticleType.DB;
import static com.ditclear.paonet.helper.annotation.ArticleType.DEVLOG;
import static com.ditclear.paonet.helper.annotation.ArticleType.FRONT_END;
import static com.ditclear.paonet.helper.annotation.ArticleType.IOS;
import static com.ditclear.paonet.helper.annotation.ArticleType.PROGRAME;
import static com.ditclear.paonet.helper.annotation.ArticleType.RECOMMAND;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 页面描述：ArticleType 文章类型
 *
 * Created by ditclear on 2017/10/17.
 */

@IntDef({ANDROID,PROGRAME,FRONT_END,IOS,DB,DEVLOG,RECOMMAND,DAILY})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface ArticleType {
    int ANDROID=16;
    int PROGRAME=6;
    int FRONT_END=5;
    int IOS=27;
    int DB=14;
    int DEVLOG=15;
    int RECOMMAND=32;
    int DAILY=9;
}
