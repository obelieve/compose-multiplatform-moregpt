@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.zxy.core.enumtype.SettingsTypeEnum
import com.zxy.core.model.local.SettingsItemBean
import composemultiplatformapigpt.composeapp.generated.resources.Res
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 *   @desc content
 *   Created by zxy
 **/

@Composable
fun SettingsScreen(statusHeight:Int,closePage:()->Unit,
                   navMyAccount:()->Unit,
                   navFeedback:()->Unit,
                   navAbout:()->Unit,
                   navWebView:(String,String)->Unit){
    val viewModels by remember { mutableStateOf(SettingsViewModel()) }
    val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
    val sessionTags by  viewModels.sessionTagsLV.observeAsState()
    val initialInfoLV by viewModels.initialInfoLV.observeAsState()
    Box(modifier = Modifier
        .background(color = co.moregpt.ai.ui.AppColor.Background)
        .fillMaxSize()
        .padding(top = statusHeightDp)) {
        Column {
            SettingsNavBar {
                closePage.invoke()
            }
            val list = mutableListOf<SettingsItemBean>()
            list.add(
                SettingsItemBean(
                    Res.drawable.ic_settings_account, "我的账号",
                    SettingsTypeEnum.MY_ACCOUNT
                )
            )
            list.add(
                SettingsItemBean(
                    Res.drawable.ic_tag, "会话标签",
                    SettingsTypeEnum.SESSION_TAG
                )
            )
            list.add(
                SettingsItemBean(
                    Res.drawable.ic_settings_feedback, "反馈",
                    SettingsTypeEnum.FEEDBACK
                )
            )
            list.add(
                SettingsItemBean(
                    Res.drawable.ic_settings_about, "关于",
                    SettingsTypeEnum.ABOUT
                )
            )
            SettingsItems(myAccountClick = {
                navMyAccount.invoke()
            }, feedbackClick = {
                navFeedback.invoke()
            },privacyClick = {
                navWebView.invoke("隐私政策","")
            }, aboutClick = {
                navAbout.invoke()
            }, (initialInfoLV?.expiresTime ?: 0L) > 0,list,sessionTags,{
                viewModels.getAllSessionTag()
            },{
                viewModels.insertSessionTag(it)
            })
        }
    }
}