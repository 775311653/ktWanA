package com.mohe.ktwana

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.squareup.leakcanary.LeakCanary

/**
 * Created by xiePing on 2018/8/21 0021.
 * Description:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initOthersSdk()
    }

    /**
     * 初始化一些三方sdk
     */
    private fun initOthersSdk() {
        //通用工具类
        Utils.init(this)
        initLeakCanary()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}