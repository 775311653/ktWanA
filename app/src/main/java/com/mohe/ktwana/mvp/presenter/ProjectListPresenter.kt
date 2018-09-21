package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.ProjectListContract
import com.mohe.ktwana.mvp.model.ProjectListModel

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectListPresenter: CommonPresenter<ProjectListContract.View>(),ProjectListContract.Presenter {

    private val model:ProjectListModel= ProjectListModel()
    override fun requestArticleResponse(page:Int,cid:Int) {
        mRootView?.showLoading()
        val disposable= model.requestArticleResponse(page,cid)
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setArticleResponse(it.data)
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