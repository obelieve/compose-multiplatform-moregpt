package co.moregpt.ai.ui.conversation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.zxy.core.cache.GlobalCacheRepository
import com.zxy.core.db.DBManager
import com.zxy.core.db.Database
import com.zxy.core.db.MessageViewEntity
import com.zxy.core.db.SessionTagViewEntity
import com.zxy.core.db.SessionViewEntity
import com.zxy.core.db.toMessage
import com.zxy.core.db.toMessageViewEntity
import com.zxy.core.db.toSession
import com.zxy.core.db.toSessionTagViewEntity
import com.zxy.core.db.toSessionViewEntity
import com.zxy.core.enumtype.MessageRoleEnum
import com.zxy.core.net.getErrorMessage
import com.zxy.core.net.requestChatGptFlow2
import com.zxy.core.sqldelight.SessionTag
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import postValue


/**
 *   @desc content
 *   Created by zxy
 **/
class ConversationViewModel : ViewModel() {

    val titleLV: MutableLiveData<String> = MutableLiveData("")
    val messageListLV: MutableLiveData<SnapshotStateList<MessageViewEntity>> = MutableLiveData(
        mutableStateListOf()
    )
    val inputtingMessageContentLV: MutableLiveData<Pair<Int, String>?> = MutableLiveData(null)
    val sendMessagingLV: MutableLiveData<Boolean> = MutableLiveData(false)

    val currentSessionIdLV: MutableLiveData<Int> = MutableLiveData(-1)
    val currentSessionLV: MutableLiveData<SessionViewEntity?> = MutableLiveData(null)
    val sessionTagsLV: MutableLiveData<MutableList<SessionTagViewEntity>> = MutableLiveData(mutableStateListOf())
    val toastLV: MutableLiveData<String?> = MutableLiveData(null)


    val rankLV: MutableLiveData<Int> = MutableLiveData(GlobalCacheRepository.initialResBean?.rank?:0)
    val sendNumLV: MutableLiveData<Int> = MutableLiveData(GlobalCacheRepository.initialResBean?.sendNumber?:0)

    private val currentMessageList: SnapshotStateList<MessageViewEntity> = mutableStateListOf()



    var startTime: Long = 0

    companion object {
        const val TAG = "ConversationViewModel"
    }



    lateinit var mDatabase:Database

    fun init(sessionId: Int? = null) {
        currentSessionIdLV.value = sessionId ?: -1

        mDatabase = DBManager.getDatabase()
        if (currentSessionIdLV.value == -1) {
            val message = MessageViewEntity(0, MessageRoleEnum.ASSISTANT.role, "嗨，你好！")
            currentMessageList.add(message)
            messageListLV.value = currentMessageList
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                currentSessionLV.value =
                    mDatabase.getSession(currentSessionIdLV.value ?: -1)?.toSessionViewEntity()
                titleLV.value = currentSessionLV.value?.title?:""
                val list =
                    mDatabase.queryAllMessageFromSessionId(currentSessionIdLV.value ?: -1)
                list.let {itList->
                    currentMessageList.addAll(itList.map { it.toMessageViewEntity() })
                    messageListLV.value = currentMessageList
                }
            }
        }
    }

    fun getAllSessionTag() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = mDatabase.getAllSessionTag()
            if (list.isNotEmpty()) {
                sessionTagsLV.postValue(list?.map { it.toSessionTagViewEntity() }?.toMutableList()?: mutableListOf())
            } else {
                sessionTagsLV.postValue(mutableListOf())
            }
//            LogUtil.i(TAG, "getAllSessionTag size=${sessionTagsLV.value?.size}")
        }
    }

    fun updateSessionTag(tag: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val sessionId = withContext(Dispatchers.IO) {
                currentSessionLV.value?.run {
                    this.tag = tag
                    mDatabase.updateSession(this@run.toSession())
                    mDatabase.insertSessionTag(SessionTag(tag))
//                    EventBus.getDefault().post(SessionTagChangedEvent(id.toLong()))
                    id
                }
            }
        }
    }

    fun deleteSession(id: Int,closePage:()->Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                currentSessionLV.value?.run {
                    val id = mDatabase.deleteSession(id)
//                    EventBus.getDefault().post(DeleteSessionEvent(id))
                }
            }
            closePage.invoke()
        }
    }

    fun sendMessage(text: String): Boolean {
        if (sendMessagingLV.value == true) {
            return false
        }
        viewModelScope.launch {
            val message = MessageViewEntity(0, MessageRoleEnum.USER.role, text)
            if (currentSessionIdLV.value == -1) {
                var session: SessionViewEntity
                val sessionId = withContext(Dispatchers.IO) {
                    session = SessionViewEntity(0, message.content, Clock.System.now().toEpochMilliseconds())
                    val id = mDatabase.insertSession(session.toSession())
                    mDatabase.updateSessionTitle(message.content, id)
//                    EventBus.getDefault().post(CreatedSessionEvent())
                    id
                }
                currentSessionIdLV.value = sessionId
                currentSessionLV.value = session
                titleLV.value = message.content
            }
            message.sessionId = (currentSessionIdLV.value ?: -1)
            withContext(Dispatchers.IO) {
                mDatabase.insertMessage(message.toMessage())
                message.id = (mDatabase.getMessage(message.id.toInt())?.id?:0L).toInt()
            }
            message.responseTime = 0
            currentMessageList.add(message)
            messageListLV.value = currentMessageList
            sendMessagingLV.value = true
            internalSendMessage(message)
        }
        return true
    }

    fun reSendMessage(message: MessageViewEntity): Boolean {
        if (sendMessagingLV.value == true) {
            return false
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                message.responseTime = 0
                mDatabase.updateMessage(message.toMessage())
                mDatabase.updateSessionTitle(
                    message.content,
                    currentSessionIdLV.value ?: -1
                )
            }
            messageListLV.value = currentMessageList
            sendMessagingLV.value = true
            internalSendMessage(message)
        }
        return true
    }
    private suspend fun internalSendMessage(message: MessageViewEntity) {
        val listMessage = currentMessageList.filter { it.role != com.zxy.core.enumtype.MessageRoleEnum.SYSTEM.role }
            .map { it.toChatMessage() }
        viewModelScope.launch(Dispatchers.IO) {
            var indexDelta = -1
            val sb = StringBuilder()
            requestChatGptFlow2(listMessage)
                .onStart {
                    viewModelScope.launch {
                        val loadingMessaging = MessageViewEntity(0, MessageRoleEnum.SYSTEM.role, "")
                        currentMessageList.add(loadingMessaging)
                        messageListLV.value = currentMessageList
                    }
                }
                /**
                 * 这个有点奇怪，buffer在onCompletion前面时，才会把背压的数据collect完成，调用onCompletion。
                 * 如果顺序反过来，会变成调用onCompletion完后，collect还在调用。
                 * 估计buffer时，会收集完成事件，但是会等待buffer走完再回调。
                 */
                .buffer(capacity = 5_000, BufferOverflow.SUSPEND)
                .onCompletion {
                    it?.let {
                        GlobalScope.launch(Dispatchers.IO) {
                            if (currentMessageList.last().inputting) {
                                currentMessageList.last().inputting = false
                                mDatabase.updateMessage(currentMessageList.last().toMessage())
                            }
                        }
                    }
                    viewModelScope.launch {
                        if (it == null) {
                            var success = true
                            currentMessageList.let {
                                //移除最后一个
                                if (it.isNotEmpty()) {
                                    if (it.last().role == com.zxy.core.enumtype.MessageRoleEnum.SYSTEM.role) {
                                        it.removeLast()
                                    } else if (it.last().inputting) {
                                        currentMessageList.last().inputting = false
                                        mDatabase.updateMessage(it.last().toMessage())
                                    }else if(currentMessageList.last().role == com.zxy.core.enumtype.MessageRoleEnum.USER.role){
                                        success = false
                                        val errorMessage = getErrorMessage(null)
                                        message.error = errorMessage
                                        message.responseTime = MessageViewEntity.RESPONSE_TIME_ERROR
                                        mDatabase.updateMessage(message.toMessage())
                                    }
                                }
                            }
                            messageListLV.value = currentMessageList
                            sendMessagingLV.value = false
                            updateMemberStatus(success)
                        }
                    }
                }.catch {
                    GlobalScope.launch(Dispatchers.IO) {
                        if (currentMessageList.last().inputting) {
                            currentMessageList.last().inputting = false
                            mDatabase.updateMessage(currentMessageList.last().toMessage())
                        }
                    }
                    viewModelScope.launch {
                        val errorMessage = getErrorMessage(it.cause)
                        message.error = errorMessage
                        message.responseTime = MessageViewEntity.RESPONSE_TIME_ERROR
                        mDatabase.updateMessage(message.toMessage())
                        currentMessageList.let {
                            //移除加载中消息
                            if (it.isNotEmpty()) {
                                if (it.last().role == com.zxy.core.enumtype.MessageRoleEnum.SYSTEM.role) {
                                    it.removeLast()
                                }
                            }
                        }
                        toastLV.value = it.cause?.message
                        messageListLV.value = currentMessageList
                        sendMessagingLV.value = false
                        updateMemberStatus(false)
                    }
                }
                .collect {
                    delay(80)
                    viewModelScope.launch {
                        if (it.choices?.size ?: 0 > 0 && (it.choices?.get(0)?.delta?.content)?.isNotEmpty()==true) {
                            val delta = it.choices?.get(0)?.delta?.content ?: ""
                            sb.append(delta)
                            val role = it.choices?.get(0)?.delta?.role ?: com.zxy.core.enumtype.MessageRoleEnum.ASSISTANT.role
                            indexDelta++
                            if (indexDelta == 0) {
                                val responseTime = Clock.System.now().epochSeconds
                                val responseMessage = MessageViewEntity(
                                    0,
                                    role,
                                    sb.toString(),
                                    sessionId = (currentSessionIdLV.value ?: -1).toInt(),
                                    responseTime = responseTime,
                                )
                                responseMessage.inputting = true
                                message.responseTime = responseTime
                                responseMessage.id = withContext(Dispatchers.IO) {
                                    mDatabase.updateMessage(message.toMessage())
                                    mDatabase.insertMessage(responseMessage.toMessage())
                                }
                                currentMessageList.let {
                                    //移除加载中消息
                                    if (it.isNotEmpty()) {
                                        if (it.last().role == com.zxy.core.enumtype.MessageRoleEnum.SYSTEM.role) {
                                            it.removeLast()
                                        }
                                    }
                                }
                                currentMessageList.add(responseMessage)
                                messageListLV.value = currentMessageList
                            } else {
                                val lastMessage = currentMessageList.last()
                                val content = sb.toString()
                                lastMessage.content = content
//                                LogUtil.e(
//                                    "flow",
//                                    "数据 indexDelta=$indexDelta,role=${role},delta = $delta,${content==lastMessage.content},content=${lastMessage.content.length}"
//                                )
                                if (lastMessage.inputting) {
                                    inputtingMessageContentLV.value =
                                        Pair(indexDelta, content)
                                }
                            }
                        }
                    }
                }
        }
    }

    private fun updateMemberStatus(success: Boolean) {
        if (success) {
            val rank = GlobalCacheRepository.initialResBean?.rank
            if (rank == 0) {
                var sendNum = GlobalCacheRepository.initialResBean?.sendNumber
                if ((sendNum ?: 0) > 0) {
                    sendNum = sendNum!! - 1
                }
                GlobalCacheRepository.initialResBean?.sendNumber = sendNum ?: 0
                GlobalCacheRepository.initialResBean = GlobalCacheRepository.initialResBean
                sendNumLV.value = sendNum ?: 0
            }
        }
    }




    fun onUserAuthorityLessEvent() {}

    override fun onCleared() {
        super.onCleared()
//        try {
//            realEventSource?.cancel()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

}