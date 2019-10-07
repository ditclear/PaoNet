package com.ditclear.paonet.helper.annotation;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.ditclear.paonet.helper.annotation.ArticleType.CXMY;
import static com.ditclear.paonet.helper.annotation.ArticleType.GITYUAN;
import static com.ditclear.paonet.helper.annotation.ArticleType.GOOGLE;
import static com.ditclear.paonet.helper.annotation.ArticleType.GUOLIN;
import static com.ditclear.paonet.helper.annotation.ArticleType.HONGYANG;
import static com.ditclear.paonet.helper.annotation.ArticleType.MEITUAN;
import static com.ditclear.paonet.helper.annotation.ArticleType.XIA;
import static com.ditclear.paonet.helper.annotation.ArticleType.YUGANG;

/**
 * 页面描述：ArticleType 文章类型
 *
 * Created by ditclear on 2017/10/17.
 */

@IntDef({HONGYANG,GUOLIN,YUGANG,CXMY,XIA,GOOGLE,MEITUAN,GITYUAN})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface ArticleType {
    int HONGYANG=408;
    int GUOLIN=409;
    int YUGANG=410;
    int CXMY=411;
    int XIA=413;
    int GOOGLE=415;
    int MEITUAN=417;
    int GITYUAN=434;
}
