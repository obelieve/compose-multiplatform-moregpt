package com.zxy.core.db

import kotlinx.datetime.Clock

/**
 *   @desc content
 *   Created by zxy
 **/
data class MessageViewEntity(public var id: Int = 0,
                             public var role: String,
                             public var content: String,
                             public var sessionId: Int = 0,
                             public var responseTime: Long = 0,
                             public var insertTime: Long = Clock.System.now().toEpochMilliseconds(),
                             public var type: String = "",
                             public var error: String = ""){
    var inputting: Boolean = false
    companion object{
        const val RESPONSE_TIME_ERROR = -1L
    }
    fun toChatMessage(): com.zxy.core.model.ChatMessage {
        return com.zxy.core.model.ChatMessage(role = role, content = content)
    }
}