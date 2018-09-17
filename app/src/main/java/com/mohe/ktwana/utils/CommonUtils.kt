package com.mohe.ktwana.utils

import android.graphics.Color
import java.util.*

/**
 * Created by xiePing on 2018/9/17 0017.
 * Description:
 */
object CommonUtils {
    /**
     * 获取随机rgb颜色值
     */
    fun randomColor(): Int {
        val random = Random()
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        var red = random.nextInt(190)
        var green = random.nextInt(190)
        var blue = random.nextInt(190)
        if (SettingUtils.isNightMode()) {
            //150-255
            red = random.nextInt(105) + 150
            green = random.nextInt(105) + 150
            blue = random.nextInt(105) + 150
        }
        return Color.rgb(red,green,blue)
    }
}