package com.ditclear.paonet.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ditclear.paonet.model.data.User
import io.reactivex.Flowable

/**
 * 页面描述：ArticleDao
 *
 * Created by ditclear on 2017/10/30.
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM Users WHERE userid= :id")
    fun getArticleById(id:Int): Flowable<User>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

}