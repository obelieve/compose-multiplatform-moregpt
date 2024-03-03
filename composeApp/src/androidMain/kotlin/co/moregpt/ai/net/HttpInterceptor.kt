package co.moregpt.ai.net

import com.zxy.core.cache.GlobalCacheRepository
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @desc http add extra data
 * created by zxy
 **/
class HttpInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = GlobalCacheRepository.token
        request = request.newBuilder().apply {
            if(request.method == "POST"){
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(request)
    }
}