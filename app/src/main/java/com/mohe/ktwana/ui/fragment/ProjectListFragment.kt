package com.mohe.ktwana.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.HomeAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.ArticleBean
import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.mvp.contract.ProjectListContract
import com.mohe.ktwana.mvp.presenter.ProjectListPresenter
import com.mohe.ktwana.ui.activity.ContentActivity
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectListFragment: BaseFragment(),ProjectListContract.View {

    private val mPresenter=ProjectListPresenter()
    lateinit var adapter:HomeAdapter
    private val datas= mutableListOf<ArticleBean>()
    private lateinit var llManager: LinearLayoutManager
    companion object {
        fun getInstance(cid:Int):ProjectListFragment{
            val fragment=ProjectListFragment()
            val args=Bundle()
            args.putInt(Constant.CONTENT_CID_KEY,cid)
            fragment.arguments=args
            return fragment
        }
    }

    private var isRefresh=false
    private var cid:Int=-1

    override fun attachLayoutRes(): Int= R.layout.fragment_refresh_layout

    override fun initView() {
        mPresenter.attachView(this)
        cid=arguments!!.getInt(Constant.CONTENT_CID_KEY)
        llManager= LinearLayoutManager(activity)
        adapter= HomeAdapter(R.layout.item_home_list,datas)
        recyclerView.run {
            adapter=this@ProjectListFragment.adapter
            layoutManager=llManager
            itemAnimator=DefaultItemAnimator()
        }
        adapter.run {
            bindToRecyclerView(recyclerView)
            setEmptyView(R.layout.fragment_empty_layout)
            onItemClickListener=this@ProjectListFragment.onItemClickListener
            onItemChildClickListener=this@ProjectListFragment.onItemChildClickListener
            setOnLoadMoreListener(loadMoreListener,recyclerView)
        }
        swipeRefreshLayout.run {
            setOnRefreshListener(refreshListener)
        }
    }

    private val onItemClickListener=object :BaseQuickAdapter.OnItemClickListener{

        override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            if (datas.size<=0) return
            val intent=Intent(activity,ContentActivity::class.java)
            val data=datas[position]
            intent.run {
                putExtra(Constant.CONTENT_URL_KEY, data.link)
                putExtra(Constant.CONTENT_TITLE_KEY, data.title)
                putExtra(Constant.CONTENT_ID_KEY, data.id)
                ActivityUtils.startActivity(this)
            }
        }

    }

    private val onItemChildClickListener=object :BaseQuickAdapter.OnItemChildClickListener{

        override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            if (datas.size<=0) return
            val data=datas[position]
            when(view?.id){
                R.id.iv_like->{
                    data.collect=!data.collect
                    adapter?.notifyDataSetChanged()
                    if (data.collect){
                        mPresenter.addCollectArticle(data.id)
                    }else{
                        mPresenter.cancelCollectArticle(data.id)
                    }

                }
            }
        }
    }
    private val loadMoreListener=object :BaseQuickAdapter.RequestLoadMoreListener{

        override fun onLoadMoreRequested() {
            isRefresh=false
            val page=adapter.data.size/15+1
            mPresenter.requestArticleResponse(page,cid)
        }

    }
    private val refreshListener=object :SwipeRefreshLayout.OnRefreshListener{
        override fun onRefresh() {
            isRefresh=true
            adapter.setEnableLoadMore(false)
            mPresenter.requestArticleResponse(1,cid)
        }

    }

    override fun lazyLoad() {
        mPresenter.requestArticleResponse(1,cid)
    }

    override fun setArticleResponse(articleResponseBean: ArticleResponseBean) {
        adapter.run {
            if (isRefresh){
                replaceData(articleResponseBean.datas)
            }else{
                addData(articleResponseBean.datas)
            }
            if (articleResponseBean.size>data.size){
                loadMoreEnd()
            }else loadMoreComplete()
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success){
            ToastUtils.showShort(R.string.collect_success)
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success){
            ToastUtils.showShort(R.string.cancel_collect_success)
        }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing=isRefresh
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing=false
        if (isRefresh){
            adapter.setEnableLoadMore(true)
        }
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }
    override fun scrollToTop() {
        recyclerView.run {
            if (llManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}