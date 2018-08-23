package com.mohe.ktwana.base

import android.app.admin.NetworkEvent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.mohe.ktwana.R
import com.mohe.ktwana.event.NetworkChangeEvent
import com.mohe.ktwana.utils.SettingUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by xiePing on 2018/8/21 0021.
 * Description:
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * 提示view
     */
    protected lateinit var mTipView:View
//    protected lateinit var mWindowManager:WindowManager
    protected lateinit var mLayoutParams:WindowManager.LayoutParams

    open lateinit var mContext:AppCompatActivity

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
     * theme color
     */
    protected var mThemeColor:Int=SettingUtils.getColor()

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
        mContext=this
        if (useEventBus()) EventBus.getDefault().register(this)
        initData()
        initTipView()
        initView()
        start()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        initColor()
    }

    /**
     * 刷新主题颜色
     */
    open fun initColor() {
        mThemeColor=if (SettingUtils.isNightMode()) resources.getColor(R.color.colorPrimary) else SettingUtils.getColor()
        BarUtils.setStatusBarColor(mContext,mThemeColor)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (SettingUtils.isNavBarHasColor()){
                BarUtils.setNavBarColor(mContext,mThemeColor)
            }else BarUtils.setNavBarColor(mContext,Color.BLACK)
        }
    }

    fun initListener() {

    }

    /**
     * 初始化TipView
     */
    private fun initTipView(){
        mTipView=layoutInflater.inflate(R.layout.layout_network_tip,null)
        mLayoutParams=WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity=Gravity.TOP
        mLayoutParams.x=0
        mLayoutParams.y=0
        mLayoutParams.windowAnimations=R.style.anim_float_view
    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetWorkChangeEvent(netEvent:NetworkChangeEvent){
        checkNetWork(netEvent.isConnected)
    }

    private fun checkNetWork(isConnected: Boolean) {
        if (enableNetworkTip()&& ActivityUtils.getTopActivity()==this){
            if (isConnected){
                doReConnected()
                if (mTipView!=null&&mTipView.parent!=null){
                    windowManager.removeView(mTipView)
                }
            }else{
                if (mTipView!=null){
                    windowManager.addView(mTipView,mLayoutParams)
                }
            }
        }
    }


}