package com.ditclear.paonet.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ditclear.paonet.model.data.Article
import io.reactivex.Single

/**
 * 页面描述：ArticleDao
 *
 * Created by ditclear on 2017/10/30.
 */
@Dao
interface ArticleDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(articles: List<Article>)

    @Query("SELECT * FROM Articles WHERE articleid= :id")
    fun getArticleById(id:Int):Single<Article>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article :Article)

}