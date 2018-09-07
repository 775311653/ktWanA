package com.mohe.ktwana.utils

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebParentLayout
import com.mohe.ktwana.R
import com.mohe.ktwana.widget.agentWebview.WebLayout

/**
 * Created by xiePing on 2018/9/7 0007.
 * Description:
 */
class Utils {
    fun getAgentWeb(activity:Activity,
                    webContent:ViewGroup,
                    layoutParams: ViewGroup.LayoutParams,
                    webChromeClient: WebChromeClient,
                    webViewClient: WebViewClient,
                    url:String){
        AgentWeb.with(activity)
                .setAgentWebParent(webContent,layoutParams)//设置agentWeb的父布局
                .useDefaultIndicator()//默认进度条
                .setWebChromeClient(webChromeClient)
                .setWebViewClient(webViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page,-1)//错误布局，点击刷新
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)//严格模式
                .setWebLayout(WebLayout(activity))//设置webView
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//京东，淘宝之类的网页会跳转到app里面，设置这个就可以让用户选择跳不跳
                .interceptUnkownUrl()//拦截错误地址
                .createAgentWeb()
                .ready()
                .go(url)
    }
}