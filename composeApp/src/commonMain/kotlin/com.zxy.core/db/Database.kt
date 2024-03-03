package com.zxy.core.db

import com.zxy.core.sqldelight.AppDatabases
import com.zxy.core.sqldelight.Message
import com.zxy.core.sqldelight.Session
import com.zxy.core.sqldelight.SessionTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

/**
 *   @desc content
 *   Created by zxy
 **/
class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabases(databaseDriverFactory.createDriver())
    private val dbQuery = database.apigptQueries

    suspend fun getMessage(id:Int): Message?{
        return withContext(Dispatchers.IO){
            val db = dbQuery.queryMessage(id.toLong())
            db.executeAsOneOrNull()
        }
    }

    suspend fun getAllMessage():List<Message>{
        return withContext(Dispatchers.IO){
            val db = dbQuery.queryAllMessage()
            db.executeAsList()
        }
    }

    suspend fun queryAllMessageFromSessionId(sessionId: Int):List<Message>{
        return withContext(Dispatchers.IO){
            val db = dbQuery.queryAllMessageFromSessionId(sessionId.toLong())
            db.executeAsList()
        }
    }

    suspend fun insertMessage(message: Message):Int {
        return withContext(Dispatchers.IO) {
            val tempMessage: Message = Message(
                message.id,
                message.role,
                message.content,
                message.sessionId,
                message.responseTime,
                Clock.System.now().toEpochMilliseconds(),
                message.type,
                message.error
            )
            dbQuery.insertMessage(
                tempMessage.role, tempMessage.content,
                tempMessage.sessionId, tempMessage.responseTime, tempMessage.insertTime,
                tempMessage.type, tempMessage.error
            )
            dbQuery.lastInsertRowId().executeAsOneOrNull()?.toInt() ?: 0
        }
    }


    suspend fun updateMessage(message: Message){
        return withContext(Dispatchers.IO){
            dbQuery.updateMessageFromId(message.role,message.content,message.sessionId,
                message.responseTime,
                message.insertTime?:0,message.type,message.error,message.id)
        }
    }

    suspend fun deleteMessage(message: Message){
        return withContext(Dispatchers.IO){
            dbQuery.deleteMessage(message.id)
        }
    }

    suspend fun getSession(id:Int): Session?{
        return withContext(Dispatchers.IO){
            dbQuery.querySession(id.toLong()).executeAsOneOrNull()
        }
    }

    suspend fun querySessionsLimit():List<Session>{
        return withContext(Dispatchers.IO){
            dbQuery.querySessions(10).executeAsList()
        }
    }

    suspend fun getAllSession():List<Session>{
        return withContext(Dispatchers.IO){
            dbQuery.queryAllSession().executeAsList()
        }
    }

    suspend fun queryAllSessionFromTag(tag:String):List<Session>{
        return withContext(Dispatchers.IO){
            dbQuery.queryAllSessionFromTag(tag).executeAsList()
        }
    }

    suspend fun insertSession(session: Session):Int{
        return withContext(Dispatchers.IO){
            dbQuery.insertSession(session.title,session.lastSessionTime,session.desc,session.tag)
            dbQuery.lastInsertRowId().executeAsOneOrNull()?.toInt()?:0
        }
    }


    suspend fun updateSession(session: Session){
        return withContext(Dispatchers.IO){
            dbQuery.updateSession(session.title,session.lastSessionTime,session.desc,session.tag,session.id)
        }
    }

    suspend fun deleteSession(session: Session){
        return withContext(Dispatchers.IO){
            dbQuery.deleteSession(session.id)
        }
    }

    suspend fun deleteSession(id: Int):Int{
        return withContext(Dispatchers.IO){
            dbQuery.deleteSession(id.toLong())
            id
        }
    }

    suspend fun updateSessionTitle(title:String,id:Int){
        return withContext(Dispatchers.IO){
            dbQuery.updateSessionTitle(title,id.toLong())
        }
    }

    suspend fun insertSessionTag(tag: SessionTag): Int {
        return withContext(Dispatchers.IO) {
            var id = -1
            try {
                dbQuery.insertSessionTag(tag.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            dbQuery.lastInsertRowId().executeAsOneOrNull()?.toInt()?:0
        }
    }

    suspend fun getAllSessionTag(): List<SessionTag> {
        return withContext(Dispatchers.IO) {
            dbQuery.queryAllSessionTag().executeAsList().map { SessionTag(it) }
        }
    }
}
