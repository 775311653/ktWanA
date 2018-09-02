package com.mohe.ktwana.base

/**
 * Created by xiePing on 2018/8/30 0030.
 * Description:
 */
interface IView {
    fun showLoading()
    fun hideLoading()
    fun showError(errorMsg: String)
}