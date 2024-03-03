package com.zxy.core.db

import app.cash.sqldelight.db.SqlDriver

/**
 *   @desc content
 *   Created by zxy
 **/

expect class DatabaseDriverFactory() {
    fun createDriver(): SqlDriver
}