package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.HomeContract
import com.mohe.ktwana.mvp.model.HomeModel

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class HomePresenter: CommonPresenter<HomeContract.View>(),HomeContract.Presenter {

    private val homeModel:HomeModel= HomeModel()

    override fun requestBanner() {
        val disposable=homeModel.requestBanner()
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setBanner(it.data)
                        }
                    }

                },{
                    mRootView?.run {
                        hideLoading()
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }

    override fun requestArticles(pageNum:Int) {
        if (pageNum==0) mRootView?.showLoading()
        val disposable=homeModel.requestArticles(pageNum)
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setArticles(it.data)
                        }
                        hideLoading()
                    }
                },{
                    mRootView?.run {
                        hideLoading()
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }

}