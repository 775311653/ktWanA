package com.mohe.ktwana.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import org.greenrobot.eventbus.EventBus

/**
 * Created by xiePing on 2018/8/21 0021.
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * 提示view
     */
    protected lateinit var mTipView:View
    protected lateinit var mWindowManager:WindowManager
    protected lateinit var mLayoutParams:WindowManager.LayoutParams

    /**
     * 布局资源文件
     */
    protected abstract fun attachLayoutRes():Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化布局
     */
    abstract fun initView()

    /**
     * 开始请求数据
     */
    abstract fun start()

    /**
     * 是否使用EventBus
     */
    open fun useEventBus():Boolean=true

    /**
     * 是否需要显示TipView
     */
    open fun enableNetworkTip():Boolean=true

    /**
     * 无网转有网状态进行自动重连，子类可重写该方法
     */
    open fun doReConnected(){
        start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(attachLayoutRes())
        if (useEventBus()) EventBus.getDefault().register(this)
        initData()
        initTipView()
        initView()
        start()
        initListener()
    }

    fun initListener() {

    }

    /**
     * 初始化TipView
     */
    private fun initTipView(){

    }


}