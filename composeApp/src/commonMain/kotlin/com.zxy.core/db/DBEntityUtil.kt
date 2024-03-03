package com.zxy.core.db

import com.zxy.core.model.ChatMessage
import com.zxy.core.sqldelight.Message
import com.zxy.core.sqldelight.Session
import com.zxy.core.sqldelight.SessionTag

/**
 *   @desc content
 *   Created by zxy
 **/

fun Message.toMessageViewEntity():MessageViewEntity{
    val message = MessageViewEntity(id.toInt(),
        role,content,sessionId.toInt(),
        responseTime,insertTime?:0L,type,error)
    return message
}
fun MessageViewEntity.toMessage():Message{
    val message = Message(id.toLong(),
        role,content,sessionId.toLong(),
        responseTime,insertTime,type,error)
    return message
}

fun SessionTag.toSessionTagViewEntity():SessionTagViewEntity{
    val sessionTag = SessionTagViewEntity(id)
    return sessionTag
}

fun SessionTagViewEntity.toSessionTag():SessionTag{
    val sessionTag = SessionTag(id)
    return sessionTag
}

fun Session.toSessionViewEntity(): SessionViewEntity{
    val session = SessionViewEntity(id,title?:"",lastSessionTime,desc,tag)
    return session
}

fun SessionViewEntity.toSession(): Session{
    val session = Session(id,title,lastSessionTime,desc,tag)
    return session
}