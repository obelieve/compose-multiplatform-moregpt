package co.moregpt.ai.net

import retrofit2.http.Body
import retrofit2.http.POST

/**
 *   @desc content
 *   Created by zxy
 **/
interface ChatApi {

    @POST("/chat/textCompletions")
    suspend fun chatCompletions(@Body body: com.zxy.core.model.ChatgptCompletionRequestBean): com.zxy.core.model.ChatCompletionResponseBean
}