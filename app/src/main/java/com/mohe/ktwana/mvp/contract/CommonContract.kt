package com.mohe.ktwana.mvp.contract

import android.view.View
import com.mohe.ktwana.base.IPresenter
import com.mohe.ktwana.base.IView

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
interface CommonContract {
    interface View:IView{
        fun showCollectSuccess(success:Boolean)
        fun showCancelCollectSuccess(success: Boolean)
    }
    interface Presenter<V:View>:IPresenter<V>{
        fun addCollectArticle(id:Int)
        fun cancelCollectArticle(id: Int)
    }
}