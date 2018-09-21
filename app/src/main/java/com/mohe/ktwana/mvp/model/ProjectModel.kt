package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.ProjectTreeBean
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectModel {

    fun requestProjectTree():Observable<HttpResult<List<ProjectTreeBean>>> {
        return RetrofitHelper.service.getProjectTree()
                .compose(SchedulerUtils.ioToMain())
    }
}