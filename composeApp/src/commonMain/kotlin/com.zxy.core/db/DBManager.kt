package com.zxy.core.db

/**
 *   @desc content
 *   Created by zxy
 **/
object DBManager {
    private val db:Database by lazy { Database(DatabaseDriverFactory()) }

    fun getDatabase():Database{
        return db
    }
}