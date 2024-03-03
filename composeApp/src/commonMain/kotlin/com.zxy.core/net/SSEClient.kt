package com.zxy.core.net

import com.zxy.core.cache.GlobalCacheRepository
import com.zxy.core.enumtype.MessageRoleEnum
import com.zxy.core.model.ChatCompletionStreamResponseBean
import com.zxy.core.model.Response.Failure
import fromJson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.cancel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 *   @desc content
 *   Created by zxy
 **/
fun getErrorMessage(e:Throwable?):String{
    var result = "发送失败，请稍后重试！"

    return e?.message?:result
}


fun requestChatGptFlow2(listMessage: List<com.zxy.core.model.ChatMessage>): Flow<ChatCompletionStreamResponseBean> {
    return callbackFlow {
        request(listMessage,object :SSECallback{
            override fun onOpen() {
                println("flow-onOpen")
            }

            override fun onClosed() {
                println("flow-onClosed")
            }

            override fun onEvent(data: String) {
                try {
                    var bean: ChatCompletionStreamResponseBean? = null
                    bean = fromJson(data,ChatCompletionStreamResponseBean()) as ChatCompletionStreamResponseBean
                    bean?.let {
                        if (bean.error == null && (it.choices?.size ?: 0) > 0) {
                            val role = it.choices?.get(0)?.delta?.role
                                ?: MessageRoleEnum.ASSISTANT.role
                            val delta = it.choices?.get(0)?.delta?.content ?: ""
                            if (delta.isNotEmpty()) {
                                trySend(it)
                            }
                        }else if(bean.error!=null){
                            val errorBean: Failure<String> =
                                fromJson(bean.error?:"",Failure("")) as Failure<String>

                            cancel(errorBean.message,
                                ApiCustomException(errorBean.code,errorBean.message)
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(e: Exception) {
                println("flow-onFailure ${e.printStackTrace()}")
            }

        })
        awaitClose {

        }
    }
}

interface SSECallback{
    fun onOpen()
    fun onClosed()
    fun onEvent(data:String)
    fun onFailure(e:Exception)
}

private suspend fun request(listMessage: List<com.zxy.core.model.ChatMessage>,callback:SSECallback){
    val client = HttpClient(){
        install(Logging)
    }
    val reqUrl = "https://api.openai.com/v1/chat/completions"
    val reqBody = com.zxy.core.model.ChatgptCompletionRequestBean(listMessage, stream = true, model = "gpt-3.5-turbo")
    val json =  Json.encodeToString(reqBody)
    println("json=$json")
    client.preparePost {

        header("Authorization",  "Bearer ${GlobalCacheRepository.token}")
        url(Url(reqUrl))
        contentType(ContentType.Application.Json)
        setBody(json)
    }.execute { httpResponse ->

        callback.onOpen()
        val channel: ByteReadChannel = httpResponse.body()

        try {
            while (!channel.isClosedForRead) {
                val message = channel.readUTF8Line(1024)
                if (message?.isNotEmpty()==true) {
                   try{
                       println("flow-$message")
                       callback.onEvent(message.replace("data: ",""))
                   }catch (e:Exception){
                       e.printStackTrace()
                   }
                }
            }
            callback.onClosed()
        }catch (e:Exception){
            callback.onFailure(e)
            channel.cancel()
        }
    }
}

