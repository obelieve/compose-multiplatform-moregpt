package com.zxy.core.model

import kotlinx.serialization.Serializable

/**
 *   @desc content
 *   Created by zxy
 **/
@Serializable
class ChatCompletionResponseBean {
    
    var message: String = ""
    
    var data: Data? = null
    
    var code: Int = 0

    @Serializable
    class Data {
        
        val choices: List<Data.Choice>? = null
        
        val created: Int = 0
        
        val id: String? = null
        
        val objectX: String? = null
        
        val usage: Data.Usage? = null
        @Serializable
        data class Choice(
            
            val finishReason: String,
            
            val index: Int,
            
            val message: ChatMessage
        )
        @Serializable
        data class Usage(
            
            val completionTokens: Int,
            
            val promptTokens: Int,
            
            val totalTokens: Int
        )
    }
}

