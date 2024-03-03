package com.zxy.core.cache.kv

/**
 *   @desc content
 *   Created by zxy
 **/
enum class StorageKeys {
    TOKEN,
    INITIAL_RES;
    val key get() = this.name
}