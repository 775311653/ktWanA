package com.mohe.ktwana.utils

import android.graphics.Color
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.mohe.ktwana.R

/**
 * Created by xiePing on 2018/8/23 0023.
 * Description:
 */
object SettingUtils {
    private val settingSp= SPUtils.getInstance()
    /**
     * 获取是否开启无图模式
     */
    fun isNoPhotoMode():Boolean{
        return settingSp.getBoolean("switch_noPhotoMode")&& NetworkUtils.isConnected()
    }
    /**
     * 获取主题颜色
     */
    fun getColor():Int{
        val defaultColor:Int=Utils.getApp().resources.getColor(R.color.colorPrimary)
        val color:Int= settingSp.getInt("color")
        return if (color!=0&&Color.alpha(color)!=255) color else defaultColor
    }
    /**
     * 设置主题颜色
     */
    fun setColor(color:Int){
        settingSp.put("color",color)
    }
    /**
     * 获取是否开启导航栏上色
     */
    fun isNavBarHasColor():Boolean{
        return settingSp.getBoolean("switch_navBarHasColor",true)
    }
    /**
     * 设置是否开启导航栏上色
     */
    fun setNavBarHasColor(b:Boolean){
        settingSp.put("switch_navBarHasColor",b)
    }
    /**
     * 设置夜间模式
     */
    fun setNightMode(b:Boolean){
        settingSp.put("switch_nightMode",b)
    }
    /**
     * 是否是夜间模式
     */
    fun isNightMode():Boolean{
        return settingSp.getBoolean("switch_nightMode")
    }
    /**
     * 是否是自动切换夜间模式
     */
    fun isAutoNightMode():Boolean{
        return settingSp.getBoolean("auto_nightMode")
    }
    /**
     * 设置自动切换夜间模式
     */
    fun setAutoNightMode(b:Boolean){
        settingSp.put("auto_nightMode",b)
    }
    fun getNightStartHour(): String {
        return settingSp.getString("night_startHour", "22")
    }

    fun setNightStartHour(nightStartHour: String) {
        settingSp.put("night_startHour", nightStartHour)
    }

    fun getNightStartMinute(): String {
        return settingSp.getString("night_startMinute", "00")
    }

    fun setNightStartMinute(nightStartMinute: String) {
        settingSp.put("night_startMinute", nightStartMinute)
    }

    fun getDayStartHour(): String {
        return settingSp.getString("day_startHour", "06")
    }

    fun setDayStartHour(dayStartHour: String) {
        settingSp.put("day_startHour", dayStartHour)
    }

    fun getDayStartMinute(): String {
        return settingSp.getString("day_startMinute", "00")
    }

    fun setDayStartMinute(dayStartMinute: String) {
        settingSp.put("day_startMinute", dayStartMinute)
    }
}