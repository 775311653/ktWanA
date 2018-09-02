package com.mohe.ktwana.rx.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class IoMainScheduler<T>:BaseScheduler<T>(Schedulers.io(),AndroidSchedulers.mainThread())