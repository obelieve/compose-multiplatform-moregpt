package co.moregpt.ai.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import co.moregpt.ai.ui.about.AboutScreen
import co.moregpt.ai.ui.account.MyAccountScreen
import co.moregpt.ai.ui.conversation.ConversationScreen
import co.moregpt.ai.ui.feedback.FeedbackScreen
import co.moregpt.ai.ui.settings.SettingsScreen
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import toastShow

/**
 *   @desc content
 *   Created by zxy
 **/
@Composable
fun AppMainScreen(statusHeight:Int,finishCall:()->Unit){
    PreComposeApp{
        val mMainViewModel by remember { mutableStateOf(MainViewModel()) }
        LaunchedEffect(true){
            mMainViewModel.init()
        }
        val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
        val navController = rememberNavigator()
        val modifier = Modifier
            .background(co.moregpt.ai.ui.AppColor.Background)
            .fillMaxSize()
            .padding(top = statusHeightDp)
        NavHost(navController, initialRoute = "main", modifier = modifier){
            scene("main"){
                MainScreen (mMainViewModel,{msg,id->
                    var route = "conversation?id=${id?:-1}"
                    if(msg?.isNotEmpty()==true){
                        route +="&msg=$msg"
                    }
                    navController.navigate(route)
                },{
                    navController.navigate("settings")
                })
            }
            scene("conversation"){backStackEntry->
                ConversationScreen(0,backStackEntry.query("id",-1)?.toInt(),
                    backStackEntry.query("msg",""),{
                        navController.popBackStack()
                    },{msg->
                        toastShow(msg)
                    })

            }
            scene("settings"){
                SettingsScreen(0,{
                    navController.popBackStack()
                },{
                    navController.navigate("my_account")
                },{
                    navController.navigate("feedback")
                },{
                    navController.navigate("about")
                },{title,url->})
            }
            scene("feedback"){
                FeedbackScreen(0,{
                    toastShow(it)
                },{
                    navController.popBackStack()
                })
            }
            scene("about"){
                AboutScreen()
            }
            scene("my_account"){
                MyAccountScreen(0) {
                    navController.popBackStack()
                }
            }
//            scene("webview?title={title}&url={url}",arguments = listOf(navArgument("title"){defaultValue = ""},
//                navArgument("url"){defaultValue = ""})){
//                val title = it.arguments?.getString("title")?:""
//                val url = it.arguments?.getString("url")?:""
//                WebViewScreen(statusHeight,title,url) {
//                    navController.popBackStack()
//                }
//            }
        }
    }

}