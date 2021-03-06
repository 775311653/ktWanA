package com.mohe.ktwana.ui.fragment

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.HomeAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.ArticleBean
import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.BannerBean
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.mvp.contract.HomeContract
import com.mohe.ktwana.mvp.presenter.HomePresenter
import com.mohe.ktwana.rx.SchedulerUtils
import com.mohe.ktwana.ui.activity.ContentActivity
import com.mohe.ktwana.widget.BannerGlideLoad
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class HomeFragment:BaseFragment(),HomeContract.View {

    private val mPresenter:HomePresenter by lazy { HomePresenter()}
    /**
     * datas
     */
    private val datas = mutableListOf<ArticleBean>()

    /**
     * banner datas
     */
    private lateinit var bannerDatas: ArrayList<BannerBean>

    private lateinit var banner:Banner

    private lateinit var homeAdapter:HomeAdapter

    private val linearLayoutManager=LinearLayoutManager(context)

    /**
     * true:下拉刷新    false:上拉加载
     */
    private var isRefresh = true

    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout

    override fun initView() {
        mPresenter.attachView(this)
        val bannerParent:LinearLayout= layoutInflater.inflate(R.layout.item_home_banner,null) as LinearLayout
        banner=bannerParent.findViewById(R.id.banner)
        banner.run{
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            setImageLoader(BannerGlideLoad())
            isAutoPlay(true)
        }
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        homeAdapter=HomeAdapter(R.layout.item_home_list,datas)
        recyclerView.adapter=homeAdapter
        recyclerView.layoutManager=this.linearLayoutManager

        homeAdapter.run {
            addHeaderView(bannerParent)
            onItemClickListener=this@HomeFragment.onItemClickListener
            onItemChildClickListener=this@HomeFragment.onItemChildClickListener
            setOnLoadMoreListener(onLoadMoreListener,recyclerView)
            setEmptyView(R.layout.fragment_empty_layout)
        }

    }

    override fun lazyLoad() {
        mPresenter.requestBanner()
        mPresenter.requestArticles(0)
    }

    override fun scrollToTop() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition()>20){
                scrollToPosition(0)
            }else smoothScrollToPosition(0)
        }
    }

    override fun setBanner(banneres: List<BannerBean>) {
        bannerDatas= banneres as ArrayList<BannerBean>
        val urls=ArrayList<String>()
        val titles=ArrayList<String>()
        Observable.fromIterable(banneres)
                .map {
                    urls.add(it.imagePath)
                    titles.add(it.title)
                }.buffer(banneres.size)
                .compose(SchedulerUtils.ioToMain())
                .subscribe {
                    banner.setImages(urls)
                    banner.setBannerTitles(titles)
                    banner.start()
                    banner.setOnBannerListener {index->
                        Intent(activity,ContentActivity::class.java).run{
                            putExtra(Constant.CONTENT_TITLE_KEY,bannerDatas[index].title)
                            putExtra(Constant.CONTENT_ID_KEY,bannerDatas[index].id)
                            putExtra(Constant.CONTENT_URL_KEY,banneres[index].url)
                            ActivityUtils.startActivity(this)
                        }
                    }
                }
    }

    override fun setArticles(articles: ArticleResponseBean) {
        articles.datas.let {
            homeAdapter.run {
                if (isRefresh){
                    replaceData(it)
                }else{
                    addData(it)
                }
                val size=it.size
                if (size<articles.size){
                    //数据到底，无法加载更多，参数表示是否显示无法加载更多的view,加载更多的时候显示，下拉刷新的时候不显示
                    loadMoreEnd(isRefresh)
                }else{
                    loadMoreComplete()
                }
            }
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            ToastUtils.showShort(resources.getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            ToastUtils.showShort(resources.getString(R.string.cancel_collect_success))
        }
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing=isRefresh
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing=false
        if (isRefresh){
            homeAdapter.setEnableLoadMore(true)
        }
    }

    override fun showError(errorMsg: String) {
        homeAdapter.run {
            if (isRefresh){
                setEnableLoadMore(true)
            }else loadMoreFail()
        }
        ToastUtils.showShort(errorMsg)
    }

    private val onRefreshListener=SwipeRefreshLayout.OnRefreshListener {
        isRefresh=true
        homeAdapter.setEnableLoadMore(false)
        mPresenter.requestBanner()
        mPresenter.requestArticles(0)
    }

    private val onLoadMoreListener=BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh=false
        swipeRefreshLayout.isRefreshing=false
        homeAdapter.setEnableLoadMore(true)
        //20条一页
        val page=homeAdapter.data.size/20
        mPresenter.requestArticles(page)
    }

    private val onItemClickListener=BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
        val intent=Intent(activity,ContentActivity::class.java)
        val articleBean:ArticleBean= datas[position]
        intent.run {
            putExtra(Constant.CONTENT_URL_KEY,articleBean.link)
            putExtra(Constant.CONTENT_TITLE_KEY,articleBean.title)
            putExtra(Constant.CONTENT_ID_KEY,articleBean.id)
            ActivityUtils.startActivity(intent)
        }
    }

    private val onItemChildClickListener=BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
        if (datas.size!=0){
            val data=datas[position]
            when(view.id){
                R.id.iv_like->{
                    if (!NetworkUtils.isConnected()){
                        ToastUtils.showShort(resources.getString(R.string.no_network))
                        return@OnItemChildClickListener
                    }
                    val collect=data.collect
                    data.collect=!collect
                    homeAdapter.setData(position,data)
                    if (data.collect){
                        mPresenter.addCollectArticle(data.id)
                    }else mPresenter.cancelCollectArticle(data.id)

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}