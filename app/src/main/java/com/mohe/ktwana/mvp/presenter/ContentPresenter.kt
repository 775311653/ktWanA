package com.mohe.ktwana.mvp.presenter

import com.mohe.ktwana.base.BasePresenter
import com.mohe.ktwana.mvp.contract.CommonContract
import com.mohe.ktwana.mvp.contract.ContentContract
import com.mohe.ktwana.mvp.model.ContentModel

/**
 * Created by xiePing on 2018/9/8 0008.
 * Description:
 */
class ContentPresenter:CommonPresenter<ContentContract.View>(),ContentContract.Presenter {

    val model:ContentModel by lazy {
        ContentModel()
    }

}