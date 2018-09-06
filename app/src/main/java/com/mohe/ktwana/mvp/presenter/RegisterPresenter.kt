package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.RegisterContract
import com.mohe.ktwana.mvp.model.RegisterModel

/**
 * Created by xiePing on 2018/9/6 0006.
 * Description:
 */
class RegisterPresenter: BasePresenter<RegisterContract.View>(),RegisterContract.Prestener {
    val mModel:RegisterModel by lazy {
        RegisterModel()
    }
    override fun registerWanAnadroid(userName: String, password: String, rePassword: String) {
        val disposable= mModel.registerWanAndroid(userName,password,rePassword)
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            showRegisterSuccess(it.data)
                        }
                    }
                },{
                    mRootView?.run {
                        showRegisterFail()
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }
}