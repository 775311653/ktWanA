package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.bean.ArticleResponseBean

/**
 * Created by xiePing on 2018/9/10 0010.
 * Description:
 */
interface KnowledgeContract {
    interface View:CommonContract.View{
        fun scrollToTop()
        fun setKnowledgeList(articleResponseBean: ArticleResponseBean)
    }
    interface Presenter:CommonContract.Presenter<View>{
        fun requestKnowledgeList(page:Int,cid:Int)
    }
}