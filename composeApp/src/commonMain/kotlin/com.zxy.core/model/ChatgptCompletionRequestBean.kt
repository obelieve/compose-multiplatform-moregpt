package com.zxy.core.model
import kotlinx.serialization.Serializable


/**
 *   @desc content
 *   Created by zxy
 **/
@Serializable
data class ChatgptCompletionRequestBean(
    
    val messages: List<ChatMessage>,

    var model: String,
    
    var stream: Boolean
//    ,
//    
//    var temperature: Float = 0.7f
)

