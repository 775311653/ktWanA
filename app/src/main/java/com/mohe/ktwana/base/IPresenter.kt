package com.mohe.ktwana.base

/**
 * Created by xiePing on 2018/8/30 0030.
 * Description:
 */
interface IPresenter<V:IView> {
    fun attachView(mRootView:V)
    fun detachView()
}