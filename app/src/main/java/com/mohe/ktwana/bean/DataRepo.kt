package com.mohe.ktwana.bean

import com.squareup.moshi.Json

/**
 * Created by xiePing on 2018/8/30 0030.
 * Description:
 */
data class HttpResult<T>(val data:T,val errorCode:Int,val errorMsg:String)