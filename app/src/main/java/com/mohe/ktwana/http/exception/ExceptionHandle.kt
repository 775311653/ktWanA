package com.mohe.ktwana.http.exception

import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class ExceptionHandle {
    companion object {
        var errorCode=ErrorStatus.UNKNOW_ERROR
        var errorMsg="请求失败，请稍后再试！"
        val TAG="retrofit"
        fun handleException(e:Throwable):String{
            LogUtils.e(e.message)
            if (e is SocketTimeoutException
                ||e is ConnectException){
                LogUtils.e(TAG,"网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            }else if (e is JsonParseException
                ||e is JSONException
                ||e is ParseException){
                LogUtils.e(TAG,"数据解析异常: " + e.message)
                errorMsg = "数据解析异常"
                errorCode = ErrorStatus.SERVER_ERROR
            }else if (e is ApiException){
                LogUtils.e(TAG,"服务器返回的错误信息:" + e.message)
                errorMsg = e.message.toString()
                errorCode = ErrorStatus.SERVER_ERROR
            }else if (e is UnknownHostException){
                LogUtils.e(TAG,"网络连接异常: " + e.message)
                errorMsg = "网络连接异常"
                errorCode = ErrorStatus.NETWORK_ERROR
            }else if (e is IllegalArgumentException){
                LogUtils.e(TAG,"参数错误: " + e.message)
                errorMsg = "参数错误"
                errorCode = ErrorStatus.SERVER_ERROR
            }else{
                try {
                    LogUtils.e(TAG,"错误: " + e.message)
                }catch (e1:Exception){
                    LogUtils.e(TAG,"未知错误Debug调试 ")
                }

                errorMsg = "未知错误，可能抛锚了吧~"
                errorCode = ErrorStatus.UNKNOW_ERROR
            }
            return errorMsg
        }
    }
}