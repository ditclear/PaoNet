package com.ditclear.paonet.helper

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.ColorRes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import android.view.ContextThemeWrapper
import android.view.View
import com.ditclear.paonet.R
import com.ditclear.paonet.helper.transitions.FabTransform
import com.ditclear.paonet.helper.transitions.MorphTransform
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.article.ArticleDetailActivity
import com.ditclear.paonet.view.auth.LoginActivity
import com.ditclear.paonet.view.search.SearchActivity


/**
 * 页面描述：页面跳转
 *
 * Created by ditclear on 2017/10/2.
 */

fun navigateToArticleDetail(activity: Activity, v: View?=null, article: Article) {
    val intent = Intent(activity, ArticleDetailActivity::class.java)
    val bundle = Bundle()
    bundle.putSerializable(Constants.KEY_SERIALIZABLE, article)
    intent.putExtras(bundle)
    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle()
    activity.startActivity(intent,options)
}

//登录
fun needsLogin(@ColorRes color: Int, triggeringView: View,activity: Activity?=null,radius:Int= (triggeringView.height / 2)) {
    var startActivity :Activity?=null
    val context: Context = triggeringView.context
    if (activity!=null){
        startActivity=activity
    }else if(context is Activity){
        startActivity=context
    }else if (context is ContextThemeWrapper){
        startActivity=context.baseContext as Activity
    }
    val login = Intent(startActivity, LoginActivity::class.java)
    val startColor = ContextCompat.getColor(context, color)
    if (triggeringView is FloatingActionButton) {
        val fabIcon = triggeringView.getTag(R.integer.fab_icon) as Int? ?: R.color.background_light
        FabTransform.addExtras(login, startColor, fabIcon)
    } else {
        MorphTransform.addExtras(login, startColor,radius)
    }
    startActivity?.let {

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                startActivity,
                triggeringView, context.resources.getString(R.string.transition_login))

        startActivity
                .startActivityForResult(login, 1, options.toBundle())
    }
}

//搜索
fun navigateToSearch(activity: Activity, v: View? = null) {
        val options = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
        activity.startActivity(Intent(activity, SearchActivity::class.java), options)
}