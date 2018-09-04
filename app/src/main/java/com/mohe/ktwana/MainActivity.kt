package com.mohe.ktwana

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.receiver.NetworkChangeReceiver
import com.mohe.ktwana.R
import com.mohe.ktwana.bean.TabEntity
import com.mohe.ktwana.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MainActivity : BaseActivity() {
    lateinit var netWorkReceiver: NetworkChangeReceiver

    var tabEntitys: ArrayList<CustomTabEntity> = arrayListOf()

    private var homeFragment: HomeFragment? = null

    override fun attachLayoutRes(): Int=R.layout.activity_main

    override fun initData() {
        registNetWorkReceiver()
        tabEntitys.add(TabEntity("首页",R.drawable.ic_home_blue_24dp,R.drawable.ic_home_black_24dp))
        tabEntitys.add(TabEntity("知识体系",R.drawable.ic_apps_blue_24dp,R.drawable.ic_apps_black_24dp))
        tabEntitys.add(TabEntity("导航",R.drawable.ic_navgation_blue_24dp,R.drawable.ic_navgation_black_24dp))
        tabEntitys.add(TabEntity("首页",R.drawable.ic_project_blue,R.drawable.ic_project_black))
    }



    override fun initView() {
        main_tab.setTabData(tabEntitys)
        main_tab.currentTab=0
        toolbar.run {
            title=getString(R.string.app_name)
            setSupportActionBar(this)
        }
        initDrawerLayout()


        refreshFragment(0)
    }


    private fun initDrawerLayout() {
        main_drawer_layout.run {
            val toggle=ActionBarDrawerToggle(mContext
                    ,this
                    ,toolbar
                    ,R.string.navigation_drawer_open
                    ,R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
    }

    private fun refreshFragment(index: Int) {
        when(index){
            0->{
                if (homeFragment==null){
                    homeFragment=HomeFragment()
                    FragmentUtils.add(supportFragmentManager, homeFragment!!,R.id.main_fl_contain)
                    FragmentUtils.show(homeFragment!!)
                }else{
                    FragmentUtils.show(homeFragment!!)
                }
            }
        }
    }

    override fun start() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_search->{
                ToastUtils.showShort("搜索")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 注册网络监听
     */
    private fun registNetWorkReceiver() {
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        netWorkReceiver=NetworkChangeReceiver()
        registerReceiver(netWorkReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(netWorkReceiver)
    }
}
