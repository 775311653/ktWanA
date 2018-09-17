package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.NavigationBean
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
class NavigationModel {
    fun requestNavigationData():Observable<HttpResult<List<NavigationBean>>> {
        return RetrofitHelper.service.getNavigationList()
                .compose(SchedulerUtils.ioToMain())
    }
}