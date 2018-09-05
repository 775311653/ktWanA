package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.LoginContract
import com.mohe.ktwana.mvp.model.LoginModel

/**
 * Created by xiePing on 2018/9/5 0005.
 * Description:
 */
class LoginPresenter: BasePresenter<LoginContract.View>(),LoginContract.Presenter {

    private var loginModel:LoginModel= LoginModel()

    override fun loginWanAndroid(userName: String, password: String) {
        mRootView?.showLoading()
        val disposable= loginModel.loginWanAndroid(userName,password)
                .subscribe ({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                            loginFail()
                        }else{
                            loginSuccess(it.data)
                        }
                        hideLoading()
                    }
                },{
                    mRootView?.run {
                        showError(ExceptionHandle.handleException(it))
                        loginFail()
                        hideLoading()
                    }

                })
        addSubscription(disposable)
    }
}