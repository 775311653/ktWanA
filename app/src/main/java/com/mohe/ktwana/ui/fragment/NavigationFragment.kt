package com.mohe.ktwana.ui.fragment

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.NavigationRvAdapter
import com.mohe.ktwana.adapter.NavigationTabAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.NavigationBean
import com.mohe.ktwana.mvp.contract.NavigationContract
import com.mohe.ktwana.mvp.presenter.NavigationPresenter
import com.mohe.ktwana.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_navigation.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
class NavigationFragment: BaseFragment(),NavigationContract.View {

    val mPresenter:NavigationPresenter= NavigationPresenter()

    lateinit var rvAdapter:BaseQuickAdapter<NavigationBean,BaseViewHolder>

    lateinit var navAdapter:NavigationTabAdapter

    val data= mutableListOf<NavigationBean>()

    var bClickTab=false
    private var bScroll: Boolean = false
    private var currentIndex: Int = 0

    private val llManager by lazy {
        LinearLayoutManager(activity)
    }

    override fun setNavigationData(list: List<NavigationBean>) {
        data.clear()
        data.addAll(list)
        rvAdapter.notifyDataSetChanged()
        navAdapter=NavigationTabAdapter(activity!!,data)
        navTab.setTabAdapter(navAdapter)
    }

    val loadingDialog by lazy {
        DialogUtils.getLoadingDialog(activity!!,"")
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

    override fun attachLayoutRes(): Int= R.layout.fragment_navigation

    override fun initView() {
        mPresenter.attachView(this)
        mPresenter.requestNavigationData()
        navTab.addOnTabSelectedListener(onTabSelectedListener)

        rvAdapter= NavigationRvAdapter(activity!!,data)

        recyclerView.run {
            adapter=rvAdapter
            layoutManager=llManager
            itemAnimator=DefaultItemAnimator()
            setHasFixedSize(true)
        }
        rvAdapter.run {
            bindToRecyclerView(recyclerView)
        }
        leftRightLink()
    }

    private fun leftRightLink() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(bScroll&&(newState==RecyclerView.SCROLL_STATE_IDLE)){
                    scrollRecyclerView()
                }
                rightLinkLeft(newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (bScroll) {
                    scrollRecyclerView()
                }
            }
        })
    }
    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance: Int = currentIndex - llManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < recyclerView!!.childCount) {
            val top: Int = recyclerView.getChildAt(indexDistance).top
            recyclerView.smoothScrollBy(0, top)
        }
    }

    val onTabSelectedListener=object : VerticalTabLayout.OnTabSelectedListener{
        override fun onTabReselected(tab: TabView?, position: Int) {
        }
        override fun onTabSelected(tab: TabView?, position: Int) {
            bClickTab=true
            selectTab(position)
        }
    }
    /**
     * Right RecyclerView link Left TabLayout
     *
     * @param newState RecyclerView Scroll State
     */
    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = llManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }
    /**
     * Smooth Right RecyclerView to Select Left TabLayout
     *
     * @param position checked position
     */
    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            navTab.setTabSelected(currentIndex)
        }
        currentIndex = position
    }


    private fun selectTab(position: Int) {
        currentIndex=position
        recyclerView.stopScroll()
        val firstIndex=llManager.findFirstVisibleItemPosition()
        val lastIndex=llManager.findLastVisibleItemPosition()
        recyclerView.run {
            if (position<=firstIndex||position>=lastIndex){
                smoothScrollToPosition(position)
            }else{
                val dy=getChildAt(position- firstIndex).top
                smoothScrollBy(0,dy)
            }
        }
    }

    override fun lazyLoad() {
    }
    fun scrollToTop() {
        navTab.setTabSelected(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView();
    }

}