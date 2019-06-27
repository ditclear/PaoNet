package com.ditclear.paonet.model.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.ditclear.paonet.helper.Constants
import com.ditclear.paonet.model.data.Article
import com.ditclear.paonet.model.data.User
import com.ditclear.paonet.model.local.dao.ArticleDao
import com.ditclear.paonet.model.local.dao.UserDao
import dev.matrix.roomigrant.GenerateRoomMigrations

/**
 * 页面描述：AppDatabase
 *
 * Created by ditclear on 2017/10/30.
 */
@Database(entities = arrayOf(Article::class,User::class),version = 1)
@GenerateRoomMigrations
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
                        AppDatabase::class.java, Constants.DB_NAME)
                        .addMigrations(*AppDatabase_Migrations.build())
                        .build()
    }

}