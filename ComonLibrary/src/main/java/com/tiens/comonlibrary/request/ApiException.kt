package com.tiens.comonlibrary.request

/**
 * @author: lhc
 * @date: 2020-03-19 21:47
 * @description 异常处理封装类
 */
class ApiException : Exception {
    var code //错误码
            : Int
    var msg //错误信息
            : String? = null
    var apiName: String? = null
    var extra //错误封装
            : Any? = null

    constructor(throwable: Throwable?, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(code: Int, msg: String?) {
        this.code = code
        this.msg = msg
    }

    constructor(code: Int, msg: String?, extra: Any?) {
        this.code = code
        this.msg = msg
        this.extra = extra
    }

    companion object {
        const val UNKNOWN_ERROR = -10000
        const val API_ERROR = 0
    }
}