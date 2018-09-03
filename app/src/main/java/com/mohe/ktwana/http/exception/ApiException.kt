package com.mohe.ktwana.http.exception

/**
 * Created by xiePing on 2018/9/2 0002.
 * Description:
 */
class ApiException : RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}