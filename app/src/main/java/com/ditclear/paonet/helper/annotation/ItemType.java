package com.ditclear.paonet.helper.annotation;

import static com.ditclear.paonet.helper.annotation.ItemType.FOOTER;
import static com.ditclear.paonet.helper.annotation.ItemType.HEADER;
import static com.ditclear.paonet.helper.annotation.ItemType.ITEM;

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

@IntDef({HEADER,ITEM,FOOTER})
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface ItemType {
    int HEADER=1;
    int ITEM=2;
    int FOOTER=3;
}
