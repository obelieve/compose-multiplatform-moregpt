package com.zxy.core.model

import kotlinx.serialization.Serializable


/**
 *   @desc content
 *   Created by zxy
 **/
@Serializable
data class ChatCompletionRequestBean(
    val messages: List<ChatMessage>
)

