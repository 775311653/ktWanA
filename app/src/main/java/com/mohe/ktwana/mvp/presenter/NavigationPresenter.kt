package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.NavigationContract
import com.mohe.ktwana.mvp.model.NavigationModel

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
class NavigationPresenter: BasePresenter<NavigationContract.View>(),NavigationContract.Presenter {

    private val model=NavigationModel()

    override fun requestNavigationData() {
        mRootView?.showLoading()
        val disposable=model.requestNavigationData()
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setNavigationData(it.data)
                        }
                        hideLoading()
                    }
                },{
                    mRootView?.showError(ExceptionHandle.handleException(it))
                })
        addSubscription(disposable)
    }

}