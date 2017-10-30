package com.ditclear.paonet.model.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.model.local.dao.ArticleDao
import com.ditclear.paonet.model.local.dao.UserDao

/**
 * 页面描述：AppDatabase
 *
 * Created by ditclear on 2017/10/30.
 */
@Database(entities = arrayOf(Article::class,User::class),version = 1)
abstract class AppDatabase :RoomDatabase(){

    abstract fun articleDao():ArticleDao

    abstract fun userDao():UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "pao.db")
                        .build()
    }

}