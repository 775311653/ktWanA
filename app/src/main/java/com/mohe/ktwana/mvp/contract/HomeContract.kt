package com.mohe.ktwana.mvp.contract

import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.BannerBean


/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
interface HomeContract {
    interface View : CommonContract.View {
        fun scrollToTop()
        fun setBanner(banneres: List<BannerBean>)
        fun setArticles(articles: ArticleResponseBean)
    }

    interface Presenter : CommonContract.Presenter<View> {
        fun requestBanner()
        fun requestArticles(pageNum: Int)
    }
}