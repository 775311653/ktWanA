package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.http.exception.ExceptionHandle
import com.mohe.ktwana.mvp.contract.KnowledgeTreeContract
import com.mohe.ktwana.mvp.model.KnowledgeTreeModel

/**
 * Created by xiePing on 2018/9/9 0009.
 * Description:
 */
class KnowledgeTreePresenter:BasePresenter<KnowledgeTreeContract.View>(),KnowledgeTreeContract.Presenter {

    val mModel:KnowledgeTreeModel by lazy {
        KnowledgeTreeModel()
    }

    override fun requestKnowledgeTree() {
        mRootView?.showLoading()
        val disposable=mModel.requestKnowledgeTree()
                .subscribe({
                    mRootView?.run {
                        if (it.errorCode!=0){
                            showError(it.errorMsg)
                        }else{
                            setKnowledgeTree(it.data)
                        }
                        hideLoading()
                    }

                },{
                    mRootView?.run {
                        showError(ExceptionHandle.handleException(it))
                    }
                })
        addSubscription(disposable)
    }

}