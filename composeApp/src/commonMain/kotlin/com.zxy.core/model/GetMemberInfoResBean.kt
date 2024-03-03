package com.zxy.core.model

import kotlinx.serialization.Serializable

/**
 *@Author zxy
 *@Date 2023/6/24
 */
@Serializable
class GetMemberInfoResBean
{
    var message:String = ""
    var data: Data? = null
    var code:Int = 0
    @Serializable
    class Data {
        val username:String = ""
        val rank:Int = 0
        val sendNumber:Int = 0
        val expiresTime:Long = 0L
    }
}
