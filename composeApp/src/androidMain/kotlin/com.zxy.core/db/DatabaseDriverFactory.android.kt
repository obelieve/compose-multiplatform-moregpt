package com.zxy.core.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import co.moregpt.ai.App
import com.zxy.core.sqldelight.AppDatabases


/**
 *   @desc content
 *   Created by zxy
 **/

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabases.Schema, App.getContext(), "apigpt.db")
    }
}