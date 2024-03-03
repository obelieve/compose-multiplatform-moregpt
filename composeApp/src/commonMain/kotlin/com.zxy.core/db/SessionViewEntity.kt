package com.zxy.core.db

/**
 *   @desc content
 *   Created by zxy
 **/
data class SessionViewEntity(var id: Long = 0,
                               var title: String = "",
                               var lastSessionTime: Long,
                               var desc: String = "",
                               var tag: String = ""){
    fun getContent(): String {
        return if(desc==null || desc == "")
             "$lastSessionTime" else
            desc
    }
}
