package com.zxy.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ChatCompletionStreamResponseBean(

    @SerialName("choices")
    val choices: List<Choice>? = null,
    @SerialName("created")
    val created: Int? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("object")
    val objectX: String? = null,
    @SerialName("model")
    val model:String? = null,

    val error:String? = null
) {
@Serializable
    data class Choice (
        @SerialName("finish_reason")
        val finishReason: String?,
        @SerialName("index")
        val index: Int,
        @SerialName("delta")
        val delta: ChatMessage?
    )
}