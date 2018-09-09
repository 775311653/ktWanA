package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.KnowledgeTreeBody
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/9 0009.
 * Description:
 */
class KnowledgeTreeModel {
    fun requestKnowledgeTree():Observable<HttpResult<List<KnowledgeTreeBody>>>{
        return RetrofitHelper.service.getKnowledgeTree()
                .compose(SchedulerUtils.ioToMain())
    }
}