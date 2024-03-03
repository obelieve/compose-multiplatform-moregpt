@file:OptIn(ExperimentalResourceApi::class)

package com.zxy.core.model.local

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 *   @desc content
 *   Created by zxy
 **/
data class MemberFeatureBean(var icon: DrawableResource, var title:String, var desc:String = "")
