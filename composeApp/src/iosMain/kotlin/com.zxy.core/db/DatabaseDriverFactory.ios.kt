package com.zxy.core.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.zxy.core.sqldelight.AppDatabases

/**
 *   @desc content
 *   Created by zxy
 **/

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabases.Schema, "apigpt.db")
    }
}