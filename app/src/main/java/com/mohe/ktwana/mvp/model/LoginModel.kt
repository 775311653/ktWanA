package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.LoginData
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable
import io.reactivex.Scheduler

/**
 * Created by xiePing on 2018/9/5 0005.
 * Description:
 */
class LoginModel {
    fun loginWanAndroid(userName:String,password:String):Observable<HttpResult<LoginData>>{
        return RetrofitHelper.service.loginWanAndroid(userName,password)
                .compose(SchedulerUtils.ioToMain())
    }
}