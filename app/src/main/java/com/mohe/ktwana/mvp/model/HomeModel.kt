package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.BannerBean
import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class HomeModel:CommonModel() {
    fun requestBanner():Observable<HttpResult<List<BannerBean>>>{
        return RetrofitHelper.service.getBanner()
                .compose(SchedulerUtils.ioToMain())
    }
    fun requestArticles(pageNum:Int):Observable<HttpResult<ArticleResponseBean>>{
        return RetrofitHelper.service.getArticles(pageNum)
                .compose(SchedulerUtils.ioToMain())
    }
}