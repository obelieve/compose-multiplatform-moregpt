package com.zxy.core.model

import kotlinx.serialization.Serializable

/**
 *   @desc content
 *   Created by zxy
 **/
@Serializable
class FeedbackResBean {
    
    var message: String = ""
    
    var data: Data? = null
    
    var code: Int = 0
    @Serializable
    class Data {
    }
}