@file:OptIn(ExperimentalResourceApi::class)

package co.moregpt.ai.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zxy.core.db.SessionTagViewEntity
import composemultiplatformapigpt.composeapp.generated.resources.Res
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 *   @desc content
 *   Created by zxy
 **/
//@Preview(showSystemUi = true)
@Composable
fun testSettingsItems() {
    val list = mutableListOf<com.zxy.core.model.local.SettingsItemBean>()
    list.add(
        com.zxy.core.model.local.SettingsItemBean(
            Res.drawable.ic_settings_account,
            "我的账号",
            com.zxy.core.enumtype.SettingsTypeEnum.MY_ACCOUNT
        )
    )
    list.add(
        com.zxy.core.model.local.SettingsItemBean(
            Res.drawable.ic_tag,
            "会话标签",
            com.zxy.core.enumtype.SettingsTypeEnum.SESSION_TAG
        )
    )
    list.add(
        com.zxy.core.model.local.SettingsItemBean(
            Res.drawable.ic_settings_feedback,
            "反馈",
            com.zxy.core.enumtype.SettingsTypeEnum.FEEDBACK
        )
    )
    list.add(
        com.zxy.core.model.local.SettingsItemBean(
            Res.drawable.ic_settings_privacy,
            "隐私政策",
            com.zxy.core.enumtype.SettingsTypeEnum.PRIVACY
        )
    )
    list.add(
        com.zxy.core.model.local.SettingsItemBean(
            Res.drawable.ic_settings_about,
            "关于",
            com.zxy.core.enumtype.SettingsTypeEnum.ABOUT
        )
    )
    SettingsItems({},{},{},{},true,list, mutableListOf(),{},{})
}

@Composable
fun SettingsItems(
                  myAccountClick:()->Unit,
                  feedbackClick:()->Unit,
                  privacyClick:()->Unit,
                  aboutClick:()->Unit,
                  isMember:Boolean,
                  list:MutableList<com.zxy.core.model.local.SettingsItemBean>, sessionTags:List<SessionTagViewEntity>,
                  sessionTagClick:()->Unit, addSessionTagClick:(String)->Unit) {
    var showSessionTagDialog by remember {
        mutableStateOf(false)
    }
    LazyColumn{
        items(list.size){
            val bean = list[it]
            when(bean.type){
                com.zxy.core.enumtype.SettingsTypeEnum.MY_ACCOUNT->{
                    SettingsItemTextType(bean) { myAccountClick.invoke() }
                }
                com.zxy.core.enumtype.SettingsTypeEnum.SESSION_TAG->{
                    SettingsItemTextType(bean) {
                        showSessionTagDialog = !showSessionTagDialog
                        if(showSessionTagDialog){
                            sessionTagClick.invoke()
                        }
                    }
                }
                com.zxy.core.enumtype.SettingsTypeEnum.FEEDBACK->{
                    SettingsItemTextType(bean) {
                        feedbackClick.invoke()
                    }
                }
                com.zxy.core.enumtype.SettingsTypeEnum.PRIVACY->{
                    SettingsItemTextType(bean) {
                        privacyClick.invoke()
                    }
                }
                com.zxy.core.enumtype.SettingsTypeEnum.ABOUT->{
                    SettingsItemTextType(bean) {
                        aboutClick.invoke()
                    }
                }
            }
            Spacer(modifier = Modifier.padding(top = 15.dp))
        }
    }
    if(showSessionTagDialog){
//        LogUtil.e("确定","showSessionTagDialog = $showSessionTagDialog,sessionTags=${sessionTags}")
        ConversationTagDialog(sessionTags = sessionTags , onCancel = {
            showSessionTagDialog = false
        }, onConfirm = {
            GlobalScope.launch(Dispatchers.IO) {
                addSessionTagClick.invoke(it)
                delay(50)
                sessionTagClick.invoke()
            }
//            LogUtil.e("确定","确定 it=$it")
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItemTextType(bean: com.zxy.core.model.local.SettingsItemBean, click:()->Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 10.dp, end = 10.dp)
        .clickable {
            click.invoke()
        },shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = co.moregpt.ai.ui.AppColor.Background2)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Image(painter = painterResource(bean.icon), contentDescription = "", modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(25.dp))
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = bean.title, color = co.moregpt.ai.ui.AppColor.Text, modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
            Image(painter = painterResource(Res.drawable.ic_settings_arrow_forward), contentDescription = "")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun SettingsItemSwitchType(bean: com.zxy.core.model.local.SettingsItemBean, checkChanged:(Boolean)->Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(start = 10.dp, end = 10.dp)
        , shape = RoundedCornerShape(15.dp), colors = CardDefaults.cardColors(containerColor = co.moregpt.ai.ui.AppColor.Background2)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            Image(painter = painterResource(bean.icon), contentDescription = "", modifier = Modifier.align(Alignment.CenterVertically))
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Text(text = bean.title, color = co.moregpt.ai.ui.AppColor.Text, modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically))
            Switch(checked = bean.switch, onCheckedChange = {
                checkChanged(it)
            })
        }
    }
}

