package com.ditclear.paonet.model.remote.api

import com.ditclear.paonet.model.data.ArticleList
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.data.UserModel
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * 页面描述：UserService
 *
 * Created by ditclear on 2017/9/29.
 */
interface UserService {

    /**
     * 登录
     * @param userid 账号
     * @param pwd 密码
     */
    @FormUrlEncoded
    @POST("login.php")
    fun login(@Field("userid") userid: String, @Field("pwd") pwd: String): Single<BaseResponse>

    @GET("checklogin.php")
    fun checkLogin(): Single<BaseResponse>

    /**
     * 退出登录
     */
    @GET("logout.php")
    fun logout(): Single<BaseResponse>

    /**
     * 用户个人信息
     * @param id 用户id
     */
    @GET("user_profile.php")
    fun userProfile(@Query("id") id: Int): Single<ResponseBody>

    /**
     * 我的个人信息
     */
    @GET("my_profile.php")
    fun myProfile(): Single<UserModel>

    /**
     * 我的文章
     * @param p 分页数
     */
    @GET("my_blog.php")
    fun myArticle(@Query("p") p: Int): Single<ArticleList>

    /**
     * 我的收藏
     * @param p 分页数
     * @param c 文章:1 ；代码：-19
     */
    @GET("my_stow.php")
    fun collectionArticle(@Query("p") p: Int, @Query("c") c: Int): Single<ArticleList>

    /**
     * 关注
     * @param userId 用户id
     */
    @GET("social.php?action=follow")
    fun followUser(@Query("id") userId: Int): Single<BaseResponse>

    /**
     * 收藏
     * @param id 文章/代码id
     */
    @GET("stow.php")
    fun stow(@Query("id") id: Int): Single<BaseResponse>

    /**
     * 是否收藏
     * @param id 文章/代码id
     */
    @GET("is_stow.php")
    fun isStow(@Query("id") id: Int): Single<BaseResponse>


    /**
     * 点赞
     * @param id id
     */
    @GET("upvote.php")
    fun praise(@Query("id") id: Int): Single<BaseResponse>
}