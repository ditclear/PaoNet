package com.ditclear.paonet.helper

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.ColorRes
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import androidx.navigation.Navigation
import com.ditclear.paonet.R
import com.ditclear.paonet.model.data.Article


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
    navigateToAuth(startActivity)
}

fun navigateToAuth(activity: Activity?){
    activity?.let {
        Navigation.findNavController(it,R.id.nav_host).navigate(R.id.action_global_loginFragment)
    }?:Log.e("navigateToSearch","activity is null")
}


//搜索
fun navigateToSearch(activity: Activity?) {
    activity?.let {
        Navigation.findNavController(it,R.id.nav_host).navigate(R.id.searchFragment)
    }?:Log.e("navigateToSearch","activity is null")

}