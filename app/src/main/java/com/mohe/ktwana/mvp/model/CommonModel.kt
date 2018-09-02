package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/8/30 0030.
 * Description:
 */
open class CommonModel {
    fun addCollectArticle(id:Int):Observable<HttpResult<Any>>{
        return RetrofitHelper.service.addCollectArticle(id)
                .compose(SchedulerUtils.ioToMain())
    }
    fun cancelCollectArticle(id: Int):Observable<HttpResult<Any>>{
        return RetrofitHelper.service.cancelCollectArticle(id)
                .compose(SchedulerUtils.ioToMain())
    }
}