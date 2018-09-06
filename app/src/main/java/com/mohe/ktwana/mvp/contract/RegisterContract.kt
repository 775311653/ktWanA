package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.base.IPresenter
import com.mohe.ktwana.base.IView
import com.mohe.ktwana.bean.LoginData

/**
 * Created by xiePing on 2018/9/6 0006.
 * Description:
 */
interface RegisterContract {
    interface View:IView{
        fun showRegisterSuccess(loginData: LoginData)
        fun showRegisterFail()
    }
    interface Prestener:IPresenter<View>{
        fun registerWanAnadroid(userName:String,password:String,rePassword:String)
    }
}