package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.base.IPresenter
import com.mohe.ktwana.base.IView
import com.mohe.ktwana.bean.KnowledgeTreeBody

/**
 * Created by xiePing on 2018/9/9 0009.
 * Description:
 */
interface KnowledgeTreeContract {
    interface View:IView{
        fun scrollToTop()
        fun setKnowledgeTree(list:List<KnowledgeTreeBody>)
    }
    interface Presenter:IPresenter<View>{
        fun requestKnowledgeTree()
    }
}