package com.zxy.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *   @desc content
 *   Created by zxy
 **/

@Serializable
data class ChatMessage(
    @SerialName("role")
    val role: String?,
    @SerialName("content")
    val content: String
)