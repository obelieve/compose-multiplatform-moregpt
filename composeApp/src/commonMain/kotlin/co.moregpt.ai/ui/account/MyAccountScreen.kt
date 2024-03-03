@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package co.moregpt.ai.ui.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import co.moregpt.ai.widget.CommonNavBar
import composemultiplatformapigpt.composeapp.generated.resources.Res
import dev.icerock.moko.mvvm.livedata.compose.observeAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 *   @desc content
 *   Created by zxy
 **/
@OptIn(ExperimentalResourceApi::class)
@Composable
fun MyAccountScreen(statusHeight:Int,closePage:()->Unit){
    val viewModels by remember { mutableStateOf(MyAccountViewModel()) }
    val statusHeightDp = with(LocalDensity.current) { statusHeight.toDp() }
    val initialInfoLV by viewModels.initialInfoLV.observeAsState()
    Box(
        modifier = Modifier
            .background(color = co.moregpt.ai.ui.AppColor.Background)
            .fillMaxSize()
            .padding(top = statusHeightDp)
    ) {
        Column {
            CommonNavBar(title = "我的账号") {
                closePage.invoke()
            }
            Row(
                Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                    .height(45.dp)) {
                Text(text = "头像",modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically), color = co.moregpt.ai.ui.AppColor.Text2,
                    style = co.moregpt.ai.ui.AppTypography.titleMedium)
                Image(
                    painterResource(Res.drawable.me),"",modifier = Modifier
                        .clip(CircleShape)
                        .size(35.dp)
                        .align(Alignment.CenterVertically))
            }
            Spacer(modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp,top = 10.dp, bottom = 10.dp).height(1.dp).background(
                co.moregpt.ai.ui.AppColor.BlackB220))
            Row(
                Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .height(45.dp)) {
                Text(text = "用户名", color = co.moregpt.ai.ui.AppColor.Text2, modifier = Modifier.align(Alignment.CenterVertically),
                    style = co.moregpt.ai.ui.AppTypography.titleMedium)
                Spacer(modifier = Modifier.weight(1f))
                Text(initialInfoLV?.username?:"用户",modifier = Modifier
                    .align(Alignment.CenterVertically),
                    style = co.moregpt.ai.ui.AppTypography.titleMedium,
                    color = co.moregpt.ai.ui.AppColor.Text)
            }

        }
    }
}