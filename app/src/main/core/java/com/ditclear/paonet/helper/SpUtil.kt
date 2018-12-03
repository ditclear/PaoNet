package com.ditclear.paonet.helper

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.ditclear.paonet.model.data.User
import com.google.gson.Gson

/**
 * Created by Administrator on 2016/4/5.
 */
object SpUtil {
    lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    var user: User ?
        get() = Gson().fromJson(prefs.getString("user", ""), User::class.java)
        set(user) = prefs.edit().putString("user", Gson().toJson(user)).apply()

    fun logout(){
        user=null
    }
}
