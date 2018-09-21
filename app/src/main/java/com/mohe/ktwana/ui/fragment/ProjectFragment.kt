package com.mohe.ktwana.ui.fragment

import com.blankj.utilcode.util.ToastUtils
import com.mohe.ktwana.R
import com.mohe.ktwana.adapter.ProjectPageAdapter
import com.mohe.ktwana.base.BaseFragment
import com.mohe.ktwana.bean.ProjectTreeBean
import com.mohe.ktwana.mvp.contract.ProjectContract
import com.mohe.ktwana.mvp.presenter.ProjectPresenter
import com.mohe.ktwana.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_project_layout.*

/**
 * Created by xiePing on 2018/9/21 0021.
 * Description:
 */
class ProjectFragment: BaseFragment(),ProjectContract.View {
    private val mPresenter=ProjectPresenter()
    lateinit var adapter: ProjectPageAdapter
    override fun attachLayoutRes(): Int = R.layout.fragment_project_layout

    override fun initView() {
        mPresenter.attachView(this)
    }

    override fun lazyLoad() {
        mPresenter.requestProjectTree()
    }

    override fun setProjectTree(datas: List<ProjectTreeBean>) {
        adapter=ProjectPageAdapter(datas,childFragmentManager)
        viewPager_project_fragment.adapter=adapter
        val titles= mutableListOf<String>()
        datas.forEach{
            titles.add(it.name)
        }
        tabLayout_project_fragment.setViewPager(viewPager_project_fragment,titles.toTypedArray())
    }

    private val loadingProgressBar by lazy {
        DialogUtils.getLoadingDialog(activity!!,"")
    }

    override fun showLoading() {
        loadingProgressBar.show()
    }

    override fun hideLoading() {
        loadingProgressBar.dismiss()
    }

    override fun showError(errorMsg: String) {
        ToastUtils.showShort(errorMsg)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    fun scrollToTop() {
        if (adapter.count == 0) {
            return
        }
        val fragment: ProjectListFragment = adapter.getItem(viewPager_project_fragment.currentItem) as ProjectListFragment
        fragment.scrollToTop()
    }
}