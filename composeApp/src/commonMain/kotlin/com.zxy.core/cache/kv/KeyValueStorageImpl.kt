package com.zxy.core.cache.kv

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getStringFlow
import com.russhwolf.settings.get
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import com.russhwolf.settings.set
import com.zxy.core.model.InitialResBean
import kotlinx.coroutines.flow.Flow

/**
 *   @desc content
 *   Created by zxy
 **/
class KeyValueStorageImpl:KeyValueStorage {
    private val settings: Settings by lazy { Settings() }
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    override var token: String?
        get() = settings[StorageKeys.TOKEN.key]
        set(value) {
            settings[StorageKeys.TOKEN.key] = value
        }
    override var initialResBean: InitialResBean?
        get() = settings.decodeValueOrNull(InitialResBean.serializer(), StorageKeys.INITIAL_RES.key)
        set(value) {
            if (value != null) {
                settings.encodeValue(InitialResBean.serializer(), StorageKeys.INITIAL_RES.key, value)
            } else {
                settings.remove(StorageKeys.TOKEN.key)
            }
        }


    override val observableToken: Flow<String>
        get() = observableSettings.getStringFlow(StorageKeys.TOKEN.key, "")

    override fun cleanStorage() {
        settings.clear()
    }
}