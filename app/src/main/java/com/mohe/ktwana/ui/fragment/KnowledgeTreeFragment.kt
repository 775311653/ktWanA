package com.mohe.ktwana.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.KnowledgeTreeAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.KnowledgeTreeBody
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.mvp.contract.KnowledgeTreeContract
import com.mohe.ktwana.mvp.presenter.KnowledgeTreePresenter
import com.mohe.ktwana.ui.activity.KnowledgeActivity
import com.mohe.ktwana.utils.DialogUtils
import com.mohe.ktwana.widget.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by xiePing on 2018/9/9 0009.
 * Description:
 */
class KnowledgeTreeFragment:BaseFragment(),KnowledgeTreeContract.View {


    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout

    val mPresenter:KnowledgeTreePresenter by lazy {
        KnowledgeTreePresenter()
    }
    lateinit var adapter:KnowledgeTreeAdapter
    var datas:MutableList<KnowledgeTreeBody> = mutableListOf()

    val linearLayoutManager:LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun initView() {
        mPresenter.attachView(this)
        adapter= KnowledgeTreeAdapter(R.layout.item_knowledgetree_list,datas)
        recyclerView.run {
            adapter=this@KnowledgeTreeFragment.adapter
            layoutManager=linearLayoutManager
            itemAnimator=DefaultItemAnimator()
            addItemDecoration(RecyclerViewItemDecoration(context,LinearLayoutManager.VERTICAL))
        }

        adapter.run {
            onItemClickListener= this@KnowledgeTreeFragment.onItemClickListener
            bindToRecyclerView(recyclerView)
            setEmptyView(R.layout.fragment_empty_layout)
        }
        swipeRefreshLayout.setOnRefreshListener { mPresenter.requestKnowledgeTree() }
    }

    private val onItemClickListener=BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        val bean=datas[position]
        Intent(activity,KnowledgeActivity::class.java).run {
            putExtra(Constant.CONTENT_TITLE_KEY,bean.name)
            putExtra(Constant.CONTENT_DATA_KEY,bean)
            ActivityUtils.startActivity(this)
        }
    }

    override fun lazyLoad() {
        mPresenter.requestKnowledgeTree()
    }

    override fun scrollToTop() {
        recyclerView?.run {
            if (linearLayoutManager.findFirstVisibleItemPosition()>20){
                scrollToPosition(0)
            }else smoothScrollToPosition(0)
        }
    }

    override fun setKnowledgeTree(list: List<KnowledgeTreeBody>) {
        adapter.replaceData(list)
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing=true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing=false
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}