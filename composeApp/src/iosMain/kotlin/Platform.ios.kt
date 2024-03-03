import dev.icerock.moko.mvvm.livedata.MutableLiveData
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getStatusBarHeight(): Int = 40

actual fun toastShow(msg:String):Unit {}

actual fun getVersionName():String  {return ""}

actual fun getScreenWidthPixels(): Int {
    return 1080
}

actual fun share(message: String) {
}

actual fun <T> MutableLiveData<T>.postValue(value: T) {
}

actual fun  <T> fromJson(json:String, classOfT:T):T {
    TODO("Not yet implemented")
}