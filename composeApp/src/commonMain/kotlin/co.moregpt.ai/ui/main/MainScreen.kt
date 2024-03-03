@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import composemultiplatformapigpt.composeapp.generated.resources.Res
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 *   @desc content
 *   Created by zxy
 **/
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MainScreen(
               viewModel:MainViewModel,
               navToConversation:(String?, Int?)->Unit,
               navToSettings:()->Unit){
    val items = listOf(
        Screen.Conversation,
        Screen.History,
    )
    val navController = rememberNavigator()
    Scaffold(  containerColor = co.moregpt.ai.ui.AppColor.Background,
        modifier = Modifier
            .fillMaxSize()
            .background(co.moregpt.ai.ui.AppColor.Background),
        topBar = {
            MainNavBar(Modifier, stringResource(Res.string.app_name),{
                navToSettings.invoke()
            })
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = co.moregpt.ai.ui.AppColor.Background,
                contentColor = contentColorFor(SnackbarDefaults.backgroundColor),
                elevation = 0.dp,
                modifier = Modifier.height(72.dp)
            ) {

                items.forEach { screen ->
                    val selected = screen.route == "conversation_list"
                    BottomNavigationItem(
                        icon = {
                            when (val icon = if(selected) screen.selectedIcon else screen.unselectedIcon) {
                                is co.moregpt.ai.ui.Icon.ImageVectorIcon -> Icon(
                                    modifier = Modifier
                                        .size(27.dp)
                                        .padding(2.dp, bottom = 4.dp),
                                    imageVector = icon.imageVector,
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                is co.moregpt.ai.ui.Icon.DrawableResourceIcon -> Icon(
                                    modifier = Modifier
                                        .size(27.dp)
                                        .padding(2.dp, bottom = 4.dp),
                                    painter = painterResource(icon.id),
                                    contentDescription = null,
                                    tint = androidx.compose.ui.graphics.Color.Unspecified
                                )
                            }
                        },
                        label = { Text(screen.title, color = if(selected) co.moregpt.ai.ui.AppColor.Main else co.moregpt.ai.ui.AppColor.Gray10) },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route)
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(navController, initialRoute =Screen.Conversation.route,modifier = Modifier.padding(padding)){
            scene(Screen.Conversation.route){
                conversationScreen(navController,viewModel,navToConversation)
            }
            scene(Screen.History.route){
                historyScreen(navController,viewModel,navToConversation)
            }
        }
    }
}
@Composable
fun conversationScreen(navController: Navigator, viewModel: MainViewModel, navToConversation:(String?, Int?)->Unit){
    val recentlySessions = viewModel.recentlySessionsLV.observeAsState()
    val list = recentlySessions.value

    MainConversationScreen(navController,list,{msg,id->
        navToConversation.invoke(msg,id)
    }) {
    }
}

@Composable
fun historyScreen(navController: Navigator,viewModel: MainViewModel,navToConversation:(String?,Int?)->Unit){
    val sessionTags by viewModel.sessionTagsLV.observeAsState()
    val historySessionTag by viewModel.historySessionTagLV.observeAsState()
    val historySessions by viewModel.historySessionsLV.observeAsState()
    HistoryScreen(navController,{msg,id->navToConversation.invoke(msg,id)},sessionTags,historySessionTag,
        historySessions,
        {
            viewModel.queryAllSessionFromTag(it)
        })
}

sealed class Screen(val route: String, val title:String, val selectedIcon: co.moregpt.ai.ui.Icon,
                    val unselectedIcon: co.moregpt.ai.ui.Icon
) {
    object Conversation : Screen("conversation_list","对话",
        co.moregpt.ai.ui.Icon.DrawableResourceIcon(co.moregpt.ai.ui.AppIcons.ChatSelected),
        co.moregpt.ai.ui.Icon.DrawableResourceIcon(co.moregpt.ai.ui.AppIcons.Chat))
    object History : Screen("history","历史",
        co.moregpt.ai.ui.Icon.DrawableResourceIcon(co.moregpt.ai.ui.AppIcons.HistorySelected),
        co.moregpt.ai.ui.Icon.DrawableResourceIcon(co.moregpt.ai.ui.AppIcons.History))
}