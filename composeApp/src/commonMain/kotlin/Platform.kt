import dev.icerock.moko.mvvm.livedata.MutableLiveData

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getStatusBarHeight(): Int

expect fun toastShow(msg:String):Unit


expect fun getVersionName():String

expect fun getScreenWidthPixels():Int

expect fun share(message:String):Unit

expect fun <T> MutableLiveData<T>.postValue(value: T)

expect fun <T> fromJson(json:String, classOfT:T):T