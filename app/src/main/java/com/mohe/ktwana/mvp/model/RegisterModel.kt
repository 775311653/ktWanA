package com.mohe.ktwana.mvp.model

import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.LoginData
import com.mohe.ktwana.http.RetrofitHelper
import com.mohe.ktwana.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xiePing on 2018/9/6 0006.
 * Description:
 */
class RegisterModel {
    fun registerWanAndroid(username:String,password:String,rePassword:String):Observable<HttpResult<LoginData>>{
        return RetrofitHelper.service.registerWanAndroid(username,password,rePassword)
                .compose(SchedulerUtils.ioToMain())
    }
}