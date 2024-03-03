package co.moregpt.ai.utils

import android.content.Context
import android.content.Intent
import android.media.MediaDrm
import android.os.Build
import android.util.Base64
import java.util.*


/**
 *   @desc content
 *   Created by zxy
 **/
object SuperUtils {

    fun getDeviceInfo():String{
        return Build.MANUFACTURER+"-"+Build.MODEL
    }
    fun getDeviceUUId(): String? {
        val wideVineUuid = UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L)
        var wvDrm: MediaDrm? = null
        return try {
            wvDrm = MediaDrm(wideVineUuid)
            val wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID)
            //optional encoding to convert the array in string.
            Base64.encodeToString(wideVineId, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            if (wvDrm != null) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        wvDrm.close()
                    } else {
                        wvDrm.release()
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }
    fun share(context: Context,message: String){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.putExtra(Intent.EXTRA_TITLE, "")
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, null)
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(shareIntent)
    }
}