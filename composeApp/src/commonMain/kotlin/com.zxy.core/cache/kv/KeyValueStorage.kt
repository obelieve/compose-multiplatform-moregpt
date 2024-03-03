package com.zxy.core.cache.kv

import com.zxy.core.model.InitialResBean
import kotlinx.coroutines.flow.Flow

/**
 *   @desc content
 *   Created by zxy
 **/
interface KeyValueStorage {
    var token: String?

    var initialResBean: InitialResBean?

    val observableToken: Flow<String>

    fun cleanStorage()
}