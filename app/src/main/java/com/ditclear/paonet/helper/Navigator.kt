package com.ditclear.paonet.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.helper.transitions.FabTransform
import com.ditclear.paonet.helper.transitions.MorphTransform
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.view.auth.LoginActivity


/**
 * 页面描述：页面跳转
 *
 * Created by ditclear on 2017/10/2.
 */

fun navigateToArticleDetail(activity: Activity?,article: Article) {
    activity?.let {
        Navigation.findNavController(it, R.id.nav_host).navigate(R.id.articleDetailActivity, Bundle().apply {
            putSerializable(Constants.KEY_SERIALIZABLE, article)
        })
    }?:Log.e("navigateToArticleDetail","activity is null")

}

fun navigateToCodeDetail(activity: Activity?,article: Article) {
    activity?.let {
        Navigation.findNavController(it,R.id.nav_host).navigate(R.id.codeDetailFragment, Bundle().apply {
            putSerializable(Constants.KEY_SERIALIZABLE, article)
        })
    }?:Log.e("navigateToCodeDetail","activity is null")

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
fun navigateToSearch(activity: Activity?) {
    activity?.let {
        Navigation.findNavController(it,R.id.nav_host).navigate(R.id.searchFragment)
    }?:Log.e("navigateToSearch","activity is null")

}