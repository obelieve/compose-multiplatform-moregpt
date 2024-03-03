import android.os.Build
import co.moregpt.ai.utils.SuperUtils
import com.google.gson.Gson
import com.obelieve.frame.utils.StatusBarUtil
import com.obelieve.frame.utils.ToastUtil
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.livedata.postValue

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getStatusBarHeight():Int = StatusBarUtil.getStatusBarHeight(co.moregpt.ai.App.getContext())

actual fun toastShow(msg:String):Unit = ToastUtil.show(msg)

actual fun getVersionName():String  {return ""}


actual fun getScreenWidthPixels(): Int {
    return co.moregpt.ai.App.getContext().resources.displayMetrics.widthPixels
}

actual fun share(message: String) {
    SuperUtils.share(co.moregpt.ai.App.getContext(), message)
}

actual fun <T> MutableLiveData<T>.postValue(value: T) {
    this.postValue(value)
}

actual fun  <T> fromJson(json:String, classOfT:T):T{
    return Gson().fromJson(json,classOfT!!::class.java)
}