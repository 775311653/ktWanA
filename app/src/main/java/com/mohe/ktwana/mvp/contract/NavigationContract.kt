package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.base.IPresenter
import com.mohe.ktwana.base.IView
import com.mohe.ktwana.bean.NavigationBean

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
interface NavigationContract {
    interface View:IView{
        fun setNavigationData(list: List<NavigationBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestNavigationData()
    }
}