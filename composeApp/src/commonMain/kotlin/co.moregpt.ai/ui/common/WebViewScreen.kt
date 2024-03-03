package co.moregpt.ai.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import co.moregpt.ai.widget.CommonNavBar

/**
 *   @desc content
 *   Created by zxy
 **/
@Composable
fun WebViewScreen(statusHeight:Int,title:String,url:String,closePage:()->Unit) {
    val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
    var titleState by remember { mutableStateOf("") }
//    val webViewState = rememberWebViewState(url)
    Box(modifier = Modifier
        .padding(top = statusHeightDp)
        .background(color = co.moregpt.ai.ui.AppColor.Background)
        .fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            CommonNavBar(titleState,{closePage.invoke()})
//            CustomWebView(url,{
//                titleState = it
//            })
//            WebView(
//                state = webViewState,
//                modifier = Modifier.fillMaxSize()
//            )
        }
    }
}