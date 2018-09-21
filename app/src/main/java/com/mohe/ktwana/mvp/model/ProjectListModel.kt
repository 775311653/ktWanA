package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectListModel {

    fun requestArticleResponse(page:Int,cid:Int):Observable<HttpResult<ArticleResponseBean>> {
        return RetrofitHelper.service.getProjectList(page,cid)
                .compose(SchedulerUtils.ioToMain())
    }
}