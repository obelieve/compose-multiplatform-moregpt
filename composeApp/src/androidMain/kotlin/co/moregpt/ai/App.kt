package co.moregpt.ai

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.zxy.core.cache.GlobalCacheRepository
import com.obelieve.frame.utils.ToastUtil
import com.obelieve.frame.utils.log.LogUtil
import com.obelieve.frame.BuildConfig

/**
 *   @desc content
 *   Created by zxy
 **/
class App: Application() {

    companion object{

        @SuppressLint("StaticFieldLeak")
        private var _context:Context? = null
        fun getContext():Context{
            return _context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        _context = this
        GlobalCacheRepository.init()
        ToastUtil.init(this)
        LogUtil.builder().setDebug(BuildConfig.DEBUG)
    }
}