package com.mohe.ktwana.utils

import android.app.ProgressDialog
import android.content.Context
import com.blankj.utilcode.util.StringUtils

/**
 * Created by xiePing on 2018/9/5 0005.
 * Description:
 */
object DialogUtils {

    /**
     * 获取一个耗时的对话框 ProgressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    fun getLoadingDialog(context: Context,msg:String):ProgressDialog{
        val dialog=ProgressDialog(context)
        if (!StringUtils.isEmpty(msg)){
            dialog.setMessage(msg)
        }
        return dialog
    }
}