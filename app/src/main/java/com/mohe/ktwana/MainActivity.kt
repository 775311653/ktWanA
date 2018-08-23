package com.mohe.ktwana

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mohe.ktwana.base.BaseActivity
import com.mohe.ktwana.receiver.NetworkChangeReceiver

class MainActivity : BaseActivity() {
    lateinit var netWorkReceiver: NetworkChangeReceiver

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        registNetWorkReceiver()
    }

    override fun start() {
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
