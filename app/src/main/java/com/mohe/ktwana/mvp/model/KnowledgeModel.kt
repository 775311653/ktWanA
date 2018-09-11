package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/10 0010.
 * Description:
 */
class KnowledgeModel {
    fun requestKnowledgeList(page: Int, cid: Int):Observable<HttpResult<ArticleResponseBean>>{
        return RetrofitHelper.service.getKnowledgeList(page,cid)
                .compose(SchedulerUtils.ioToMain())
    }
}