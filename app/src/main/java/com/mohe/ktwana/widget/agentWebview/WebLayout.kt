package com.mohe.ktwana.widget.agentWebview

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebView
import com.just.agentweb.IWebLayout
import com.mohe.ktwana.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * Created by xiePing on 2018/9/7 0007.
 * Description:
 */
class WebLayout: IWebLayout<WebView, ViewGroup> {

    constructor()

    lateinit var mActivity:Activity
    lateinit var mWebView:WebView
    lateinit var parentView:SmartRefreshLayout
    constructor(activity: Activity){
        mActivity=activity
        parentView= mActivity.layoutInflater.inflate(R.layout.fragment_smart_refresh_web,null) as SmartRefreshLayout
        mWebView=parentView.findViewById(R.id.webView)
    }

    override fun getLayout(): ViewGroup {
        return parentView
    }

    override fun getWebView(): WebView {
        return mWebView
    }

}