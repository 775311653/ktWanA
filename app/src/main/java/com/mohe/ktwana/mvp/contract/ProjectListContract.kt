package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.bean.ArticleResponseBean

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
interface ProjectListContract {

    interface View : CommonContract.View {
        fun scrollToTop()
        fun setArticleResponse(articleResponseBean: ArticleResponseBean)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun requestArticleResponse(page:Int,cid:Int)
    }
}