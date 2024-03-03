package com.zxy.core.model

import kotlinx.serialization.Serializable

/**
 *@Author zxy
 *@Date 2023/4/29
 */
@Serializable
class CheckVersionResBean {
    
    var message: String = ""
    
    var data: com.zxy.core.model.CheckVersionResBean.Data? = null
    
    var code: Int = 0

    @Serializable
    class Data {
        
        var title:String = ""
        
        var content:String = ""
        
        var needUpdate:Boolean = false
        
        var versionCode:Int = 0
        
        var downloadUrl:String = ""
    }
}