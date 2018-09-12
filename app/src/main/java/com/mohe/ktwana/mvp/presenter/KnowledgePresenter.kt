package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.KnowledgeContract
import com.mohe.ktwana.mvp.model.KnowledgeModel

/**
 * Created by xiePing on 2018/9/10 0010.
 * Description:
 */
class KnowledgePresenter:CommonPresenter<KnowledgeContract.View>(),KnowledgeContract.Presenter {

    private val model:KnowledgeModel by lazy {
        KnowledgeModel()
    }

    override fun requestKnowledgeList(page: Int, cid: Int) {
        mRootView?.showLoading()
        val disposable= model.requestKnowledgeList(page,cid)
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setKnowledgeList(it.data)
                        }
                        hideLoading()
                    }
                },{
                    mRootView?.run {
                        showError(ExceptionHandle.handleException(it))
                        hideLoading()
                    }
                })
        addSubscription(disposable)
    }
}