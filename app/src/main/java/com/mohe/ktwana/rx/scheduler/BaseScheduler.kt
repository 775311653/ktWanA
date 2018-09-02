package com.mohe.ktwana.rx.scheduler

import io.reactivex.*
import org.reactivestreams.Publisher

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
open class BaseScheduler<T> constructor(val subscribeOnScheduler:Scheduler
                                   ,val observabeOnScheduler: Scheduler):ObservableTransformer<T,T>,
        SingleTransformer<T,T>,
        MaybeTransformer<T,T>,
        CompletableTransformer,
        FlowableTransformer<T,T> {
    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observabeOnScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observabeOnScheduler)
    }

    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observabeOnScheduler)
    }

    override fun apply(upstream: Completable): CompletableSource {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observabeOnScheduler)
    }

    override fun apply(upstream: Flowable<T>): Publisher<T> {
        return upstream.subscribeOn(subscribeOnScheduler)
                .observeOn(observabeOnScheduler)
    }

}