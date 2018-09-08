package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.base.IView

/**
 * Created by xiePing on 2018/9/8 0008.
 * Description:
 */
interface ContentContract {
    interface View:CommonContract.View{

    }
    interface Presenter:CommonContract.Presenter<View>{

    }
}