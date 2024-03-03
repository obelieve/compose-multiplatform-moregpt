@file:OptIn(ExperimentalResourceApi::class)

package com.zxy.core.model.local

import com.zxy.core.enumtype.SettingsTypeEnum
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 *   @desc content
 *   Created by zxy
 **/
data class SettingsItemBean(var icon: DrawableResource, var title:String, var type: SettingsTypeEnum, var switch:Boolean = true)
