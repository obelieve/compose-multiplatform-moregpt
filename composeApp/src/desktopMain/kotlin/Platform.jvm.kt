class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getStatusBarHeight(): Int = 40

actual fun toastShow(msg:String):Unit {}

actual fun getVersionName():String  {return ""}