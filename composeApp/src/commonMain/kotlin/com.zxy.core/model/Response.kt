package com.zxy.core.model

import com.zxy.core.net.NetErrorCode
import kotlinx.serialization.Serializable


/**
 *@Author zxy
 *@Date 2023/4/30
 */
@Serializable
sealed class Response{
   data class Success<T>(var data:T, var code:Int = NetErrorCode.SUCCESS, var message:String = "请求成功"): com.zxy.core.model.Response()
    data class Failure<T>(var data:T, val code:Int = NetErrorCode.FAILURE, var message:String="请求失败"): com.zxy.core.model.Response()
}