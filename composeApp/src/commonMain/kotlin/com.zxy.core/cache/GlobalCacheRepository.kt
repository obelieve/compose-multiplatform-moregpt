package com.zxy.core.cache

import com.zxy.core.cache.kv.KeyValueStorage
import com.zxy.core.cache.kv.KeyValueStorageImpl

/**
 *   @desc content
 *   Created by zxy
 **/
object GlobalCacheRepository {
    val keyValueStorage: KeyValueStorage = KeyValueStorageImpl()
    var token = "openai API KEY"

var initialResBean: com.zxy.core.model.InitialResBean? = null
    set(value) {
        field = value
        try {
            keyValueStorage.initialResBean = value
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun init() {
        try{
            initialResBean = keyValueStorage.initialResBean
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


}