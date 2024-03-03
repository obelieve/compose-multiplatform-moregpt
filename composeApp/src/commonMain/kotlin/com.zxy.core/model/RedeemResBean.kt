package com.zxy.core.model

import kotlinx.serialization.Serializable

/**
 *@Author zxy
 *@Date 2023/4/29
 */
@Serializable
class RedeemResBean{
    var message:String = ""
    var data: Data? = null
    var code:Int = 0
    @Serializable
    class Data{
        var expiresTime:Long = 0
    }
}