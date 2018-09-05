package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.base.IPresenter
import com.mohe.ktwana.base.IView
import com.mohe.ktwana.bean.LoginData

/**
 * Created by xiePing on 2018/9/5 0005.
 * Description:
 */
interface LoginContract {
    interface View:IView{
        fun loginSuccess(loginData: LoginData)
        fun loginFail()
    }
    interface Presenter:IPresenter<View>{
        fun loginWanAndroid(userName:String,password:String)
    }
}