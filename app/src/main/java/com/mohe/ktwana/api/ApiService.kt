package com.mohe.ktwana.api

import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.BannerBean
import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.LoginData
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by xiePing on 2018/9/1 0001.
 * Description:
 */
interface ApiService {
    /**
     * 收藏站内文章
     * http://www.wanandroid.com/lg/collect/1165/json
     * @param id 文章id
     */
    @POST("/lg/collect/{id}/json")
    fun addCollectArticle(@Path("id") id:Int):Observable<HttpResult<Any>>

    /**
     * 文章列表中取消收藏文章
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     * @param id 文章id
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int):Observable<HttpResult<Any>>

    /**
     * 获取轮播图
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    fun getBanner():Observable<HttpResult<List<BannerBean>>>

    /**
     * 获取文章列表
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum
     */
    @GET("article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum:Int):Observable<HttpResult<ArticleResponseBean>>

    /**
     * 登录
     * http://www.wanandroid.com/user/login
     * @param username
     * @param password
     */
    @POST("user/login")
    @FormUrlEncoded
    fun loginWanAndroid(@Field("username") userName:String,
                        @Field("password") password:String):Observable<HttpResult<LoginData>>
}