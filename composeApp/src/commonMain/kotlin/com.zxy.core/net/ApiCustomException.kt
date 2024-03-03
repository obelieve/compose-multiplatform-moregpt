package com.zxy.core.net

/**
 *   @desc content
 *   Created by zxy
 **/
data class ApiCustomException(val code:Int, override val message:String): Exception(message) {
}