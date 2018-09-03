package com.mohe.ktwana.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.HomeAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.ArticleBean
import com.mohe.ktwana.bean.ArticleResponseBean
import com.mohe.ktwana.bean.BannerBean
import com.mohe.ktwana.mvp.contract.HomeContract
import com.mohe.ktwana.mvp.presenter.HomePresenter
import com.mohe.ktwana.widget.BannerGlideLoad
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        homeAdapter=HomeAdapter(R.layout.item_home_list,datas)
        homeAdapter.run {
            addHeaderView(bannerParent)
        }
        recyclerView.adapter=homeAdapter
        recyclerView.layoutManager=LinearLayoutManager(context)
        mPresenter.requestBanner()
        mPresenter.requestArticles(0)
    }

    override fun lazyLoad() {

    }

    override fun scorllToTop() {

    }

    override fun setBanner(banneres: List<BannerBean>) {
        bannerDatas= banneres as ArrayList<BannerBean>
        val urls=ArrayList<String>()
        val titles=ArrayList<String>()
        Observable.fromIterable(banneres)
                .map {
                    urls.add(it.imagePath)
                    titles.add(it.title)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    banner.setImages(urls)
                    banner.setBannerTitles(titles)
                    banner.start()
                }
    }

    override fun setArticles(articles: ArticleResponseBean) {
        articles.datas.let {
            homeAdapter.run {
                addData(it)
            }
        }
    }

    override fun showCollectSuccess(success: Boolean) {

    }

    override fun showCancelCollectSuccess(success: Boolean) {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(errorMsg: String) {

    }
}