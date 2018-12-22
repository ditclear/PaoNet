package com.ditclear.paonet.model.repository

import com.ditclear.paonet.model.data.ArticleList
import com.ditclear.paonet.model.data.BaseResponse
import com.ditclear.paonet.model.data.UserModel
import com.ditclear.paonet.model.local.dao.UserDao
import com.ditclear.paonet.model.remote.api.UserService
import io.reactivex.Single
import okhttp3.ResponseBody


/**
 * 页面描述：UserRepository
 *
 * Created by ditclear on 2017/10/30.
 */
class UserRepository (private val remote: UserService, private val local: UserDao) {

    fun login(userid: String, pwd: String): Single<BaseResponse> = remote.login(userid, pwd)

    fun checkLogin(): Single<BaseResponse> = remote.checkLogin()

    /**
     * 退出登录
     */
    fun logout(): Single<BaseResponse> = remote.logout()

    /**
     * 用户个人信息
     * @param id 用户id
     */
    fun userProfile(id: Int): Single<ResponseBody> = remote.userProfile(id)

    /**
     * 我的个人信息
     */
    fun myProfile(): Single<UserModel> = remote.myProfile()

    /**
     * 我的文章
     * @param p 分页数
     */
    fun myArticle(p: Int): Single<ArticleList> = remote.myArticle(p)

    /**
     * 我的收藏
     * @param p 分页数
     * @param c 文章:1 ；代码：-19
     */
    fun collectionArticle(p: Int, c: Int): Single<ArticleList> = remote.collectionArticle(p, c)

    /**
     * 关注
     * @param userId 用户id
     */
    fun followUser(userId: Int): Single<BaseResponse> = remote.followUser(userId)

    /**
     * 收藏
     * @param id 文章/代码id
     */
    fun stow(id: Int): Single<BaseResponse> = remote.stow(id)

    /**
     * 是否收藏
     * @param id 文章/代码id
     */
    fun isStow(id: Int): Single<BaseResponse> = remote.isStow(id)


    /**
     * 点赞
     * @param id id
     */
    fun praise(id: Int): Single<BaseResponse> = remote.praise(id)

}