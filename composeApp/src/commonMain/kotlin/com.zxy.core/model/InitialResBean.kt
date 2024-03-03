package com.zxy.core.model
import kotlinx.serialization.Serializable

@Serializable
data class InitialResBean(
    
    val expiresTime: Long,
    
    val rank: Int,
    
    var sendNumber: Int,
    
    val token: String,
    
    val username: String
)