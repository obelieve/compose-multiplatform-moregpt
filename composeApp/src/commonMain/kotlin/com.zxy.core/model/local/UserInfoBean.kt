@file:OptIn(ExperimentalResourceApi::class)

package com.zxy.core.model.local

import composemultiplatformapigpt.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi


/**
 *   @desc content
 *   Created by zxy
 **/
data class UserInfoBean(var user:String, var headerImage: DrawableResource = Res.drawable.ic_user, var rank:Int, var expiresTime:Long,)
