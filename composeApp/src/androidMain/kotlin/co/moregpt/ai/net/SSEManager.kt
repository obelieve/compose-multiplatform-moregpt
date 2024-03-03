package co.moregpt.ai.net

import co.moregpt.ai.net.sse.RealEventSourceExtend
import com.obelieve.frame.net.gson.MGson
import com.obelieve.frame.utils.log.LogUtil
import com.zxy.core.cache.GlobalCacheRepository
import com.zxy.core.enumtype.MessageRoleEnum
import com.zxy.core.model.ChatCompletionStreamResponseBean
import com.zxy.core.model.Response
import com.zxy.core.net.ApiCustomException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8

/**
 *   @desc content
 *   Created by zxy
 **/
object SSEManager{
var realEventSource: RealEventSourceExtend? = null
    private fun requestChatGptFlow(request: Request): Flow<ChatCompletionStreamResponseBean> {
        if (realEventSource != null) {
            realEventSource?.cancel()
        }
        return callbackFlow {
            val gson = MGson.newGson()
            realEventSource =
                RealEventSourceExtend(request = request, listener = object : EventSourceListener() {

                    override fun onOpen(eventSource: EventSource, response: okhttp3.Response) {
                        super.onOpen(eventSource, response)
                        LogUtil.e("flow","onOpen")
                    }

                    override fun onClosed(eventSource: EventSource) {
                        super.onClosed(eventSource)
                        LogUtil.e("flow","onClosed")
                        channel.close()
                    }

                    override fun onEvent(
                        eventSource: EventSource,
                        id: String?,
                        type: String?,
                        data: String
                    ) {
                        super.onEvent(eventSource, id, type, data)
                        LogUtil.e("flow","onEvent")
                        try {
                            var bean: ChatCompletionStreamResponseBean? = null
                            bean = gson.fromJson(data, ChatCompletionStreamResponseBean::class.java)
                            bean?.let {
                                if (bean.error == null && (it.choices?.size ?: 0) > 0) {
                                    val role = it.choices?.get(0)?.delta?.role
                                        ?: MessageRoleEnum.ASSISTANT.role
                                    val delta = it.choices?.get(0)?.delta?.content ?: ""
                                    if (delta.isNotEmpty()) {
                                        trySend(it)
                                    }
                                }else if(bean.error!=null){
                                    val errorBean = gson.fromJson(bean.error?:"",
                                        Response.Failure::class.java)
                                    cancel(errorBean.message,
                                        ApiCustomException(errorBean.code,errorBean.message)
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(
                        eventSource: EventSource,
                        t: Throwable?,
                        response: okhttp3.Response?
                    ) {
                        super.onFailure(eventSource, t, response)
                        LogUtil.e("flow","",t)
                        cancel(CancellationException("API Error", t))
                    }
                })
            try {
                val sseResponse = realEventSource?.syncConnect(NetApiManager.okHttpClient)
                sseResponse?.let {
                    realEventSource?.syncProcessResponse(it)
                }
            } catch (e: Exception) {

                LogUtil.e("flow","",e)
                channel.close(e)
            }

            awaitClose {
                realEventSource?.cancel()
            }
        }
    }

    private fun getChatGptRequest(listMessage: List<com.zxy.core.model.ChatMessage>): Request {
        val url = NetURLConstant.BASE_URL + NetURLConstant.CHAT_COMPLETIONS
        val reqBody = com.zxy.core.model.ChatgptCompletionRequestBean(listMessage, stream = true, model = "gpt-3.5-turbo")
        val json = MGson.newGson().toJson(reqBody)
        LogUtil.e("SSE", "send json=$json")
        val bString: ByteString = json.encodeUtf8()
        val requestBody = RequestBody.create(NetApiManager.JsonMediaType, bString)
        /**
         * okhttp 自动会加上charset=UTF-8，需要使用 ByteString
         */
        val request = Request.Builder().apply {
            addHeader("Authorization", "Bearer ${GlobalCacheRepository.token}")
            url(url)
            post(requestBody)
        }.build()
        return request
    }
}