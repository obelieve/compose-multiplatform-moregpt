package co.moregpt.ai.utils


/**
 *   @desc content
 *   Created by zxy
 **/
object FormatUtils {

//    private val format = SimpleDateFormat("yyyy.MM.dd")

    fun getyyyyMMdd(millsTime:Long):String{
//        return format.format(Date(millsTime))
        return "$millsTime"
    }
}