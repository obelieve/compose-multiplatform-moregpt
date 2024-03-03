package com.zxy.core.model

import kotlinx.serialization.Serializable


/**
 * @Author zxy
 * @Date 2023/4/29
 */
@Serializable
data class CheckVersionReqBean( var versionCode:Int)