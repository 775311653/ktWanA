package com.mohe.ktwana.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
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

    /**
     * 获取一个Dialog
     *
     * @param context
     * @return
     */
    fun getDialog(context: Context):AlertDialog.Builder{
        return AlertDialog.Builder(context)
    }

    fun getConfirmDialog(context: Context,message:String,
                         onClickListener: DialogInterface.OnClickListener):AlertDialog.Builder{
        val builder= getDialog(context)
        builder.setMessage(message)
        builder.setPositiveButton("确定",onClickListener)
        builder.setNegativeButton("取消",null)
        return builder
    }

    /**
     * 获取一个耗时的对话框 ProgressDialog
     *
     * @param context
     * @param message
     * @return
     */
    fun getWaitDialog(context: Context,msg: String):ProgressDialog{
        val progressDialog=ProgressDialog(context)
        if (!StringUtils.isEmpty(msg)){
            progressDialog.setMessage(msg)
        }
        return progressDialog
    }
}