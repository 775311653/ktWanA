package com.mohe.ktwana.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.KnowledgeAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.ArticleBean
import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.mvp.contract.KnowledgeContract
import com.mohe.ktwana.mvp.presenter.KnowledgePresenter
import com.mohe.ktwana.ui.activity.ContentActivity
import com.mohe.ktwana.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by xiePing on 2018/9/10 0010.
 * Description:
 */
class KnowledgeFragment:BaseFragment(),KnowledgeContract.View {

    private val mPresenter:KnowledgePresenter by lazy {
        KnowledgePresenter()
    }

    private val datas:MutableList<ArticleBean> = arrayListOf()

    private val adapter:KnowledgeAdapter by lazy {
        KnowledgeAdapter(context!!,datas)
    }

    private lateinit var linearLayoutManager:LinearLayoutManager

    private var isRefresh:Boolean=true

    /**
     * cid
     */
    private var cid: Int = 0

    companion object {
        fun getInstance(cid:Int): KnowledgeFragment {
            val fragment= KnowledgeFragment()
            val args=Bundle()
            args.putInt(Constant.CONTENT_CID_KEY,cid)
            fragment.arguments=args
            return fragment
        }
    }


    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout

    override fun initView() {
        mPresenter.attachView(this)
        linearLayoutManager =LinearLayoutManager(context)
        cid=arguments?.getInt(Constant.CONTENT_CID_KEY)?:0
        recyclerView.run {
            adapter=this@KnowledgeFragment.adapter
            layoutManager=linearLayoutManager
        }

        adapter.run {
            setOnLoadMoreListener(onRequestLoadMoreListener,recyclerView)
            onItemClickListener=this@KnowledgeFragment.onItemClickListener
            onItemChildClickListener=this@KnowledgeFragment.onItemChildClickListener
        }

        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        mPresenter.requestKnowledgeList(0,cid)
    }

    private val onItemClickListener=BaseQuickAdapter.OnItemClickListener{adapter, view, position ->
        val intent= Intent(activity, ContentActivity::class.java)
        val articleBean:ArticleBean= datas[position]
        intent.run {
            putExtra(Constant.CONTENT_URL_KEY,articleBean.link)
            putExtra(Constant.CONTENT_TITLE_KEY,articleBean.title)
            putExtra(Constant.CONTENT_ID_KEY,articleBean.id)
            ActivityUtils.startActivity(intent)
        }
    }

    private val onItemChildClickListener=BaseQuickAdapter.OnItemChildClickListener{adapter, view, position ->
        val articleBean=datas[position]
        when(view.id){
            R.id.iv_like->{
                if (!isLogin){
                    ActivityUtils.startActivity(LoginActivity::class.java)
                    ToastUtils.showShort(R.string.login_tint)
                    return@OnItemChildClickListener
                }
                if (articleBean.collect){
                    mPresenter.cancelCollectArticle(articleBean.id)
                    articleBean.collect=false
                }else {
                    mPresenter.addCollectArticle(articleBean.id)
                    articleBean.collect=true
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun lazyLoad() {
    }

    override fun scrollToTop() {
        if (linearLayoutManager.findFirstVisibleItemPosition()>20){
            recyclerView.scrollToPosition(0)
        }else recyclerView.smoothScrollToPosition(0)
    }

    override fun setKnowledgeList(articleResponseBean: ArticleResponseBean) {
        adapter.run {
            if (isRefresh){
                replaceData(articleResponseBean.datas)
                setEnableLoadMore(true)
            }else{
                addData(articleResponseBean.datas)
            }
            if (articleResponseBean.datas.size<articleResponseBean.size){
                loadMoreEnd(isRefresh)//当第一页数据就小于默认的一页数据，就不显示没有更多的布局
            }else loadMoreComplete()
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        ToastUtils.showShort(R.string.collect_success)
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        ToastUtils.showShort(R.string.cancel_collect_success)
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing=true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing=false
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
        if (isRefresh){
            adapter.setEnableLoadMore(true)
        }else{
            adapter.loadMoreFail()
        }
    }

    private val onRefreshListener= SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        adapter.setEnableLoadMore(false)
        mPresenter.requestKnowledgeList(0,cid)
    }

    private val onRequestLoadMoreListener=BaseQuickAdapter.RequestLoadMoreListener{
        isRefresh=false
        val page=adapter.data.size/20
        mPresenter.requestKnowledgeList(page,cid)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}