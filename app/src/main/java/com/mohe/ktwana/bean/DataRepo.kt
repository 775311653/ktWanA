package com.mohe.ktwana.bean

import com.squareup.moshi.Json

/**
 * Created by xiePing on 2018/8/30 0030.
 * Description:
 */
data class HttpResult<T>(val data:T,val errorCode:Int,val errorMsg:String)

/**
 * 轮播图
 */
data class BannerBean(
        val desc:String,
        val id:Int,
        val imagePath:String,
        val isVisible:Int,
        val order:Int,
        val title:String,
        val type:Int,
        val url:String
)

data class ArticleResponseBean(
        val curPage:Int,
        val datas:MutableList<ArticleBean>,
        val offset: Int,
        val over: Boolean,
        val pageCount: Int,
        val size: Int,
        val total: Int
)

//文章
data class ArticleBean(
        val apkLink: String,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val origin: String,
        val projectLink: String,
        val publishTime: Long,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: MutableList<TagBean>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
)


data class TagBean(
        val name: String,
        val url: String
)

// 登录数据
data class LoginData(
        val collectIds: List<Any>,
        val email: String,
        val icon: String,
        val id: Int,
        val password: String,
        val type: Int,
        val username: String
)

//知识体系
data class KnowledgeTreeBody(
        val children: MutableList<Knowledge>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val visible: Int
)

data class Knowledge(
        val children: List<Any>,
        val courseId: Int,
        val id: Int,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val visible: Int
)