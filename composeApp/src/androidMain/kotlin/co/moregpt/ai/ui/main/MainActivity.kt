package co.moregpt.ai.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.obelieve.frame.utils.StatusBarUtil
import getStatusBarHeight

/**
 *   @desc content
 *   Created by zxy
 **/
class MainActivity: ComponentActivity() {

    var statusHeight = 0

    companion object {
        const val TAG = "MainActivity"
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBarTranslucentStatus(this)
        StatusBarUtil.setWindowLightStatusBar(this, false)
        statusHeight = getStatusBarHeight()
        setContent {
            AppMainScreen(statusHeight,{
                finish()
            })
        }
//        EventBus.getDefault().register(this)
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onSessionTagChangedEvent(event: SessionTagChangedEvent){
//        mMainViewModel.apply {
//            LogUtil.i(TAG,"onSessionTagChangedEvent")
//            queryAllSessionFromTag("")
//            getAllSessionTag()
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onSessionTagAddEvent(event: SessionTagAddEvent){
//        mMainViewModel.apply {
//            LogUtil.i(TAG,"SessionTagAddEvent")
//            getAllSessionTag()
//        }
//    }
//
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onDeleteSessionEvent(event: DeleteSessionEvent){
//        mMainViewModel.apply {
//            LogUtil.i(TAG,"onDeleteSessionEvent")
//            querySessionsLimit()
//            queryAllSessionFromTag("")
//            getAllSessionTag()
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onCreatedSessionEvent(event: CreatedSessionEvent){
//        mMainViewModel.apply {
//            LogUtil.i(TAG,"onCreatedSessionEvent")
//            querySessionsLimit()
//            queryAllSessionFromTag("")
//            getAllSessionTag()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
//        EventBus.getDefault().unregister(this)
    }




}