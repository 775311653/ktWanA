package com.mohe.ktwana

import android.content.DialogInterface
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.receiver.NetworkChangeReceiver
import com.mohe.ktwana.bean.TabEntity
import com.mohe.ktwana.constant.Constant
import com.mohe.ktwana.event.LoginEvent
import com.mohe.ktwana.ui.activity.LoginActivity
import com.mohe.ktwana.ui.fragment.HomeFragment
import com.mohe.ktwana.ui.fragment.KnowledgeTreeFragment
import com.mohe.ktwana.ui.fragment.NavigationFragment
import com.mohe.ktwana.ui.fragment.ProjectFragment
import com.mohe.ktwana.utils.DialogUtils
import com.mohe.ktwana.utils.Preference
import com.mohe.ktwana.utils.SettingUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : BaseActivity(), View.OnClickListener {


    lateinit var netWorkReceiver: NetworkChangeReceiver

    var tabEntitys: ArrayList<CustomTabEntity> = arrayListOf()
    private var userName:String by Preference(Constant.USERNAME_KEY, "")

    private var homeFragment: HomeFragment? = null
    private var knowledgeTreeFragment: KnowledgeTreeFragment? =null
    private var navigationFragment: NavigationFragment? =null
    private var projecFragment: ProjectFragment?=null
    var mIndex=0

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
        main_tab.setOnTabSelectListener(onTabSelectListener)
        toolbar.run {
            title=getString(R.string.app_name)
            setSupportActionBar(this)
        }
        initDrawerLayout()
        refreshFragment(main_tab.currentTab)
        main_nav_view.run {
            getHeaderView(0).setOnClickListener(this@MainActivity)
            val nav_username:TextView =getHeaderView(0).findViewById(R.id.tv_user_name)
            nav_username.text = if (!isLogin) {
                getString(R.string.login)
            } else {
                userName
            }
            setNavigationItemSelectedListener(onDrawerNavgationItemSelectorListener)
        }

        main_floating_btn.setOnClickListener(onFABClickListener)

    }

    private val onTabSelectListener:OnTabSelectListener=object: OnTabSelectListener{
        //切换不同的标签
        override fun onTabSelect(position: Int) {
            refreshFragment(position)
        }

        //重点一次相同的标签
        override fun onTabReselect(position: Int) {
            refreshFragment(position)
        }

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
            0->{//首页
                if (homeFragment==null){
                    homeFragment=HomeFragment()
                    FragmentUtils.add(supportFragmentManager, homeFragment!!,R.id.main_fl_contain)
                    FragmentUtils.showHide(homeFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }else{
                    FragmentUtils.showHide(homeFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }
            }
            1->{//知识体系
                if (knowledgeTreeFragment==null){
                    knowledgeTreeFragment= KnowledgeTreeFragment()
                    FragmentUtils.add(supportFragmentManager, knowledgeTreeFragment!!,R.id.main_fl_contain)
                    FragmentUtils.showHide(knowledgeTreeFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }else{
                    FragmentUtils.showHide(knowledgeTreeFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }
            }
            2->{
                if (navigationFragment==null){
                    navigationFragment= NavigationFragment()
                    FragmentUtils.add(supportFragmentManager, navigationFragment!!,R.id.main_fl_contain)
                    FragmentUtils.showHide(navigationFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }else{
                    FragmentUtils.showHide(navigationFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }
            }
            3->{
                if (projecFragment==null){
                    projecFragment= ProjectFragment()
                    FragmentUtils.add(supportFragmentManager, projecFragment!!,R.id.main_fl_contain)
                    FragmentUtils.showHide(projecFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }else{
                    FragmentUtils.showHide(projecFragment!!,FragmentUtils.getFragments(supportFragmentManager))
                }
            }
        }
    }

    private val onFABClickListener=View.OnClickListener {
        when(main_tab.currentTab){
            0-> homeFragment?.scrollToTop()
            1-> knowledgeTreeFragment?.scrollToTop()
            2-> navigationFragment?.scrollToTop()
            3-> projecFragment?.scrollToTop()
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

    override fun onClick(p0: View?) {
        if (p0==main_nav_view.getHeaderView(0)){
            if (!isLogin){
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
        }
    }



    private val onDrawerNavgationItemSelectorListener= NavigationView.OnNavigationItemSelectedListener{
        when(it.itemId){
            R.id.nav_logout->{
                logout()
            }
            R.id.nav_night_mode->{
                if (SettingUtils.isNightMode()){
                    SettingUtils.setNightMode(false)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }else{
                    SettingUtils.setNightMode(true)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                recreate()
            }
        }
        true
    }

    private fun logout() {
        DialogUtils.getConfirmDialog(this,getString(R.string.confirm_logout),
                DialogInterface.OnClickListener{ _, _ ->//这是onClick方法里面的两个参数，没用到就不设置名字
                    val waitDialog=DialogUtils.getLoadingDialog(this,"")
                    waitDialog.show()
                    doAsync {
                        isLogin=false
                        userName=""
                        uiThread {
                            EventBus.getDefault().post(LoginEvent(false))
                            waitDialog.dismiss()
                            ToastUtils.showShort(getString(R.string.logout_success))
                            ActivityUtils.startActivity(LoginActivity::class.java)
                        }
                    }
                }).show()
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

    @Subscribe (threadMode = ThreadMode.MAIN)
    fun refreshLoginStatus(loginEvent: LoginEvent){
        if (loginEvent.isLogin){
            tv_user_name.text=userName
            main_nav_view.menu.findItem(R.id.nav_logout).isVisible=true
            homeFragment?.lazyLoad()
        }else{
            tv_user_name.text=resources.getString(R.string.login)
            main_nav_view.menu.findItem(R.id.nav_logout).isVisible=false
            homeFragment?.lazyLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(netWorkReceiver)
    }
}
