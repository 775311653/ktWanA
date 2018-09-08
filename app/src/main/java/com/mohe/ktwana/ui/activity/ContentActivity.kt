package com.mohe.ktwana.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.just.agentweb.AgentWeb
import com.mohe.ktwana.R
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.mvp.contract.ContentContract
import com.mohe.ktwana.mvp.presenter.ContentPresenter
import com.mohe.ktwana.utils.AgentWebUtil
import com.mohe.ktwana.utils.DialogUtils
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.main_fl_contain.*

class ContentActivity : BaseActivity(),ContentContract.View {

    override fun attachLayoutRes(): Int =R.layout.activity_content

    lateinit var contentTitle:String
    lateinit var contentUrl:String
    var contentId:Int=0
    lateinit var mAgentWeb: AgentWeb

    val mPresenter:ContentPresenter by lazy {
        ContentPresenter()
    }

    override fun initData() {
        contentTitle=intent.getStringExtra(Constant.CONTENT_TITLE_KEY)
        contentUrl=intent.getStringExtra(Constant.CONTENT_URL_KEY)
        contentId=intent.getIntExtra(Constant.CONTENT_ID_KEY,0)
    }

    override fun initView() {
        mPresenter.attachView(this)
        mAgentWeb=AgentWebUtil.getAgentWeb(this,
                main_fl_contain,
                ViewGroup.LayoutParams(-1,-1),
                webChromClient,
                webViewClient,
                contentUrl)
        toolbar.run {
            title=getString(R.string.loading)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun start() {
    }

    override fun showCollectSuccess(success: Boolean) {
        ToastUtils.showShort(getString(R.string.collect_success))
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        ToastUtils.showShort(getString(R.string.cancel_collect_success))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share->{
                val intent=Intent()
                intent.run {
                    action=Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                            getString(R.string.share_article_url,
                                    getString(R.string.app_name),
                                    contentTitle,
                                    contentUrl))
                    type=Constant.CONTENT_SHARE_TYPE
                    ActivityUtils.startActivity(Intent.createChooser(this,getString(R.string.action_share)))
                }
            }
            R.id.action_like->{
                if (isLogin){
                    mPresenter.addCollectArticle(contentId)
                }else{
                    ActivityUtils.startActivity(LoginActivity::class.java)
                    ToastUtils.showShort(getString(R.string.login_tint))
                }
            }
            R.id.action_browser->{
                Intent().run {
                    action=Intent.ACTION_VIEW
                    data=Uri.parse(contentUrl)
                    ActivityUtils.startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val loadingDialog by lazy {
        DialogUtils.getLoadingDialog(this,"")
    }

    override fun showLoading() {
        loadingDialog.show()
    }

    override fun hideLoading() {
        loadingDialog.dismiss()
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb.handleKeyEvent(keyCode,event)){
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    //在复写父类字方法时，要用object声明是一个新的对象
    val webChromClient:WebChromeClient= object :WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            toolbar.title=title
        }
    }

    val webViewClient:WebViewClient= object :WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }
    }

    override fun onPause() {
        super.onPause()
        mAgentWeb.webLifeCycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.webLifeCycle.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        mAgentWeb.webLifeCycle.onDestroy()
    }
}
