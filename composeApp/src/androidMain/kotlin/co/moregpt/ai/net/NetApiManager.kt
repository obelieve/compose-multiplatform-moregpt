package co.moregpt.ai.net

import co.moregpt.ai.net.NetURLConstant.BASE_URL
import com.obelieve.frame.net.HttpUtil
import com.obelieve.frame.net.convert.ApiCustomGsonConverterFactory
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *   @desc content
 *   Created by zxy
 **/
object NetApiManager {

    private val mChatApi by lazy {
        RxJavaPlugins.setErrorHandler {}
        val httpUtil: HttpUtil = HttpUtil.build().baseUrl(BASE_URL)
        httpUtil
            .addInterceptor(HttpInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ApiCustomGsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .create(ChatApi::class.java)
    }

    val okHttpClient = OkHttpClient.Builder().apply {
        this.connectTimeout(10, TimeUnit.SECONDS)
        this.readTimeout(3, TimeUnit.MINUTES)
    }.build()

    val JsonMediaType = "application/json".toMediaTypeOrNull() //application/json;charset=UTF-8


    fun getChatApi(): ChatApi {
        return mChatApi
    }
}