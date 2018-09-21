package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.base.IPresenter
import com.mohe.ktwana.base.IView
import com.mohe.ktwana.bean.HttpResult
import com.mohe.ktwana.bean.ProjectTreeBean

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
interface ProjectContract {
    interface View:IView{
        fun setProjectTree(datas:List<ProjectTreeBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestProjectTree()
    }
}