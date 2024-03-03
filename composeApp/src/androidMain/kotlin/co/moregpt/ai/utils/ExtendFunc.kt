package co.moregpt.ai.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import com.obelieve.frame.utils.log.LogUtil

/**
 *   @desc 扩展函数
 *   Created by zxy
 **/
object ExtendFunc {
    @Stable
    inline val  Int.uxPx: Dp
        get() = Dp((this@uxPx*(0.75f).toFloat())).apply {
//        LogUtil.e("px dp","px转dp=${this.value}")
    }
}


val Context.connectivityManager: ConnectivityManager get() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(any: Any? = null) {
    Toast.makeText(this, "$any", Toast.LENGTH_LONG).show()
}

fun Context.toast(res: Int) {
    Toast.makeText(this, res, Toast.LENGTH_LONG).show()
}