package com.mohe.ktwana.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by xiePing on 2018/8/30 0030.
 * Description:
 */
class BasePresenter<V:IView>:IPresenter<V> {
    var mRootView:V?=null

    private var compositeDisposable=CompositeDisposable()

    override fun attachView(mRootView: V) {
        this.mRootView=mRootView
    }

    override fun detachView() {
        mRootView=null
        if (!compositeDisposable.isDisposed){
            compositeDisposable.clear()
        }
    }

    fun addSubscription(disposable: Disposable){
        compositeDisposable.add(disposable)
    }
}