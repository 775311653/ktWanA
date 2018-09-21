package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.ProjectContract
import com.mohe.ktwana.mvp.model.ProjectModel

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectPresenter: BasePresenter<ProjectContract.View>(),ProjectContract.Presenter {

    private val model=ProjectModel()

    override fun requestProjectTree() {
        mRootView?.showLoading()
        val disposable=model.requestProjectTree()
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setProjectTree(it.data)
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