package com.mohe.ktwana.rx

import com.mohe.ktwana.rx.scheduler.IoMainScheduler

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
object SchedulerUtils {
    fun <T>ioToMain():IoMainScheduler<T>{
        return IoMainScheduler()
    }
}