package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.base.IView
import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.CommonContract
import com.mohe.ktwana.mvp.model.CommonModel

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
open class CommonPresenter<V:CommonContract.View>
    : BasePresenter<V>(),CommonContract.Presenter<V> {

    private val mModel:CommonModel by lazy { CommonModel() }

    override fun addCollectArticle(id: Int) {
        val disposable=mModel.addCollectArticle(id)
                .subscribe({result->
                    mRootView?.run {
                        if (result.errorCode!=0){
                            showError(result.errorMsg)
                        }else showCollectSuccess(true)
                    }

                },{t->
                    mRootView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(disposable)
    }

    override fun cancelCollectArticle(id: Int) {
        val disposable=mModel.cancelCollectArticle(id)
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else showCancelCollectSuccess(true)
                    }
                },{
                    mRootView?.apply {
                        hideLoading()
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }
}